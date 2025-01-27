package com.ljz.compilationVSM.domain.ObjQuestion.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.common.exception.BizExceptionCodeEnum;
import com.ljz.compilationVSM.common.utils.SnowflakeIdGenerator;
import com.ljz.compilationVSM.common.utils.UserContextHolder;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.ObjCheckRequestDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.ObjCheckResponseDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.ObjQueryDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.ObjResponseDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.service.ObjQuestionService;
import com.ljz.compilationVSM.domain.convert.ObjQuestionDTOMapping;
import com.ljz.compilationVSM.infrastructure.mapper.ChooseMapper;
import com.ljz.compilationVSM.infrastructure.mapper.FillMapper;
import com.ljz.compilationVSM.infrastructure.po.ChoosePO;
import com.ljz.compilationVSM.infrastructure.po.FillPO;
import com.ljz.compilationVSM.infrastructure.po.ObjAnswerPO;
import com.ljz.compilationVSM.infrastructure.po.StudentPO;
import com.ljz.compilationVSM.infrastructure.repository.ChooseRepository;
import com.ljz.compilationVSM.infrastructure.repository.FillRepository;
import com.ljz.compilationVSM.infrastructure.repository.ObjAnswerRepository;
import com.ljz.compilationVSM.infrastructure.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 客观题服务类
 *
 * @author ljz
 * @since 2025-01-13
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ObjQuestionServiceImpl implements ObjQuestionService {

    private final ChooseMapper chooseMapper;
    private final FillMapper fillMapper;
    private final ObjQuestionDTOMapping objQuestionDTOMapping;
    private final ChooseRepository chooseRepository;
    private final FillRepository fillRepository;
    private final ObjAnswerRepository objAnswerRepository;
    private final StudentRepository studentRepository;
    private final SnowflakeIdGenerator idGenerator;

    /**
     * 填空题作答内容分割符
     */
    @Value("${obj-question.delimiter}")
    private String fillDelimiter;

    /**
     * 选择题单题分值
     */
    @Value("${obj-question.choose-weight}")
    private Integer chooseWeight;

    /**
     * 填空题单题分值
     */
    @Value("${obj-question.fill-weight}")
    private Integer fillWeight;


    @Override
    public ObjResponseDTO getObjQuestion(ObjQueryDTO objQueryDTO) {
        ObjResponseDTO responseDTO = new ObjResponseDTO();
        // 获取客观题题目
        // todo 暂未指定题目类型
        List<ChoosePO> chooseList = chooseMapper.getChoose(objQueryDTO.getChooseNum());
        responseDTO.setChooseList(objQuestionDTOMapping.chooseListConvert(chooseList));
        List<FillPO> fillList = fillMapper.getFill(objQueryDTO.getFillNum());
        responseDTO.setFillList(objQuestionDTOMapping.fillListConvert(fillList));
        // 获取判题记录
        if (isPractise()) {
            responseDTO.setPractise(1);
        } else {
            responseDTO.setPractise(0);
        }
        return responseDTO;
    }

    @Override
    @Transactional
    public ObjCheckResponseDTO checkObjQuestion(ObjCheckRequestDTO requestDTO) {
        ObjCheckResponseDTO responseDTO = new ObjCheckResponseDTO();
        // 客观题校验 (由于题目数据量不大，所以采用一次性查询完再分别校验，减轻数据库压力)
        // 选择题校验
        LambdaQueryWrapper<ChoosePO> queryWrapper = Wrappers.<ChoosePO>lambdaQuery()
                .select(ChoosePO::getKeyAnswer, ChoosePO::getId)
                .eq(ChoosePO::getIsDelete, Boolean.FALSE);
        Map<Long, String> chooseKeyAnswerList = chooseRepository.list(queryWrapper).stream()
                .collect(Collectors.toMap(ChoosePO::getId, ChoosePO::getKeyAnswer));
        List<ObjCheckRequestDTO.Answer> chooseAnswerList = requestDTO.getChooseAnswer();
        List<ObjCheckResponseDTO.ResultUnit> chooseResult = new ArrayList<>();
        chooseAnswerList.forEach(item -> {
            String keyAnswer = chooseKeyAnswerList.get(item.getId());
            if(Objects.isNull(keyAnswer)){
                log.error("校验客观题内容,id为 {} 的选择题不存在",item.getId().toString());
                throw new BizException(BizExceptionCodeEnum.ILLEGAL_OBJ_QUESTION_ID_ERROR);
            }
            if (keyAnswer.substring(1).equals(item.getAnswer())) {
                chooseResult.add(new ObjCheckResponseDTO.ResultUnit(null, 1));
            } else {
                chooseResult.add(new ObjCheckResponseDTO.ResultUnit(keyAnswer.substring(0, 1), 0));
            }
        });
        // 填空题校验
        LambdaQueryWrapper<FillPO> queryWrapper2 = Wrappers.<FillPO>lambdaQuery()
                .select(FillPO::getKeyAnswer, FillPO::getId)
                .eq(FillPO::getIsDelete, Boolean.FALSE);
        Map<Long, String> fillKeyAnswerList = fillRepository.list(queryWrapper2).stream()
                .collect(Collectors.toMap(FillPO::getId, FillPO::getKeyAnswer));
        List<ObjCheckRequestDTO.Answer> fillAnswerList = requestDTO.getFillAnswer();
        List<ObjCheckResponseDTO.ResultUnit> fillResult = new ArrayList<>();
        fillAnswerList.forEach(item -> {
            String keyAnswer = fillKeyAnswerList.get(item.getId());
            if(Objects.isNull(keyAnswer)){
                log.error("校验客观题内容,id为 {} 的填空题不存在",item.getId().toString());
                throw new BizException(BizExceptionCodeEnum.ILLEGAL_OBJ_QUESTION_ID_ERROR);
            }
            if (keyAnswer.equals(item.getAnswer())) {
                fillResult.add(new ObjCheckResponseDTO.ResultUnit(null, 1));
            } else {
                fillResult.add(new ObjCheckResponseDTO.ResultUnit(keyAnswer, 0));
            }
        });
        // 判断是否是练习
        if (!isPractise()) {
            // 计算得分情况
            Integer chooseGrade = (int) ((chooseResult.stream().filter(item -> item.getMark().equals(1)).count()) * chooseWeight);
            Integer fillGrade = (int) ((fillResult.stream().filter(item -> item.getMark().equals(1)).count()) * fillWeight);
            responseDTO.setScore(chooseGrade + fillGrade);
            // 答题情况入库
            ObjAnswerPO objAnswerPO = new ObjAnswerPO();
            String chooseIdList = chooseAnswerList.stream().map(item -> item.getId().toString())
                    .collect(Collectors.joining(","));
            String chooseAnswer = chooseAnswerList.stream().map(ObjCheckRequestDTO.Answer::getAnswer)
                    .collect(Collectors.joining(","));
            String fillIdList = fillAnswerList.stream().map(item -> item.getId().toString())
                    .collect(Collectors.joining(","));
            String fillAnswer = fillAnswerList.stream().map(ObjCheckRequestDTO.Answer::getAnswer)
                    .collect(Collectors.joining(fillDelimiter));
            objAnswerPO.setId(idGenerator.generate());
            objAnswerPO.setChooseIdList(chooseIdList);
            objAnswerPO.setChooseAnswerList(String.join(chooseAnswer));
            objAnswerPO.setFillIdList(fillIdList);
            objAnswerPO.setFillAnswerList(fillAnswer);
            objAnswerPO.setUserId(UserContextHolder.getUserId());
            objAnswerPO.setChooseGrade(chooseGrade);
            objAnswerPO.setFillGrade(fillGrade);
            objAnswerRepository.save(objAnswerPO);
            LambdaUpdateWrapper<StudentPO> updateWrapper = Wrappers.<StudentPO>lambdaUpdate()
                    .set(StudentPO::getObjGrade, chooseGrade + fillGrade)
                    .eq(StudentPO::getIsDelete, Boolean.FALSE)
                    .eq(StudentPO::getUserId, UserContextHolder.getUserId());
            studentRepository.update(updateWrapper);

        }
        responseDTO.setChooseResultList(chooseResult);
        responseDTO.setFillResultList(fillResult);
        return responseDTO;
    }

    /**
     * 判断用户客观题是否是练习模式
     *
     * @return 是否是练习模式
     */
    private boolean isPractise() {
        Long userId = UserContextHolder.getUserId();
        LambdaQueryWrapper<ObjAnswerPO> queryWrapper1 = Wrappers.<ObjAnswerPO>lambdaQuery()
                .select(ObjAnswerPO::getId)
                .eq(ObjAnswerPO::getIsDelete, Boolean.FALSE)
                .eq(ObjAnswerPO::getUserId, userId);
        ObjAnswerPO answerPO = objAnswerRepository.getOne(queryWrapper1);
        return Objects.nonNull(answerPO);
    }
}
