package com.ljz.compilationVSM.domain.ObjQuestion.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ljz.compilationVSM.common.constant.Constant;
import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.common.exception.BizExceptionCodeEnum;
import com.ljz.compilationVSM.common.utils.SnowflakeIdGenerator;
import com.ljz.compilationVSM.common.utils.UserContextHolder;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.*;
import com.ljz.compilationVSM.domain.ObjQuestion.service.ObjQuestionService;
import com.ljz.compilationVSM.domain.convert.ObjQuestionDTOMapping;
import com.ljz.compilationVSM.infrastructure.mapper.ChooseMapper;
import com.ljz.compilationVSM.infrastructure.mapper.FillMapper;
import com.ljz.compilationVSM.infrastructure.po.*;
import com.ljz.compilationVSM.infrastructure.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
    private final TeacherRepository teacherRepository;

    /**
     * 填空题作答内容分割符
     */
    @Value("${obj-question.delimiter}")
    private String answerDelimiter;

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
            if (Objects.isNull(keyAnswer)) {
                log.error("校验客观题内容,id为 {} 的选择题不存在", item.getId().toString());
                throw new BizException(BizExceptionCodeEnum.ILLEGAL_OBJ_QUESTION_ID_ERROR);
            }
            if (keyAnswer.equals(item.getAnswer())) {
                chooseResult.add(new ObjCheckResponseDTO.ResultUnit(null, 1));
            } else {
                chooseResult.add(new ObjCheckResponseDTO.ResultUnit(keyAnswer, 0));
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
            if (Objects.isNull(keyAnswer)) {
                log.error("校验客观题内容,id为 {} 的填空题不存在", item.getId().toString());
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
                    .collect(Collectors.joining(answerDelimiter));
            String fillIdList = fillAnswerList.stream().map(item -> item.getId().toString())
                    .collect(Collectors.joining(","));
            String fillAnswer = fillAnswerList.stream().map(ObjCheckRequestDTO.Answer::getAnswer)
                    .collect(Collectors.joining(answerDelimiter));
            objAnswerPO.setId(idGenerator.generate());
            objAnswerPO.setChooseIdList(chooseIdList);
            objAnswerPO.setChooseAnswerList(chooseAnswer);
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

    @Override
    public ObjAnswerInfoResponseDTO getObjAnswerInfo(String number) {
        String logPrefix = "教师查看学生客观题答题情况";
        // 校验
        StudentPO studentPO = verify(number, logPrefix);
        // 查询客观题答题情况和题目内容
        LambdaQueryWrapper<ObjAnswerPO> objAnswerQueryWrapper = Wrappers.<ObjAnswerPO>lambdaQuery()
                .select(ObjAnswerPO::getChooseIdList, ObjAnswerPO::getChooseAnswerList, ObjAnswerPO::getFillIdList, ObjAnswerPO::getFillAnswerList)
                .eq(ObjAnswerPO::getIsDelete, Boolean.FALSE)
                .eq(ObjAnswerPO::getUserId, studentPO.getUserId());
        ObjAnswerPO answerPO = objAnswerRepository.getOne(objAnswerQueryWrapper);
        if (Objects.isNull(answerPO)) {
            log.info(logPrefix+",客观题答题信息不存在,student number = {}", number);
            throw new BizException(BizExceptionCodeEnum.OBJ_ANSWER_NOT_EXIST);
        }
        List<String> chooseIdList = Arrays.stream(answerPO.getChooseIdList().split(",", -1)).toList();
        String[] chooseAnswerList = answerPO.getChooseAnswerList().split(answerDelimiter, -1);
        List<String> fillIdList = Arrays.stream(answerPO.getFillIdList().split(",", -1)).toList();
        String[] fillAnswerList = answerPO.getFillAnswerList().split(answerDelimiter, -1);
        LambdaQueryWrapper<ChoosePO> chooseQuestionQueryWrapper = Wrappers.<ChoosePO>lambdaQuery()
                .select(ChoosePO::getId, ChoosePO::getTitle, ChoosePO::getChoice0, ChoosePO::getChoice1, ChoosePO::getChoice2, ChoosePO::getChoice3, ChoosePO::getKeyAnswer)
                .eq(ChoosePO::getIsDelete, Boolean.FALSE)
                .in(ChoosePO::getId, chooseIdList);
        Map<Long, ChoosePO> chooseQuestionMap = chooseRepository.list(chooseQuestionQueryWrapper).stream().collect(Collectors.toMap(ChoosePO::getId, item -> item));
        LambdaQueryWrapper<FillPO> fillQuestionQueryWrapper = Wrappers.<FillPO>lambdaQuery()
                .select(FillPO::getId, FillPO::getTitle, FillPO::getKeyAnswer)
                .eq(FillPO::getIsDelete, Boolean.FALSE)
                .in(FillPO::getId, fillIdList);
        Map<Long, FillPO> fillQuestionMap = fillRepository.list(fillQuestionQueryWrapper).stream().collect(Collectors.toMap(FillPO::getId, item -> item));
        // 构造返回结果
        List<ObjAnswerInfoResponseDTO.ChooseResponseDTO> chooseResponseDTOS = new ArrayList<>(chooseIdList.size());
        for (int index = 0; index < chooseIdList.size(); index++) {
            ObjAnswerInfoResponseDTO.ChooseResponseDTO chooseResponseDTO = new ObjAnswerInfoResponseDTO.ChooseResponseDTO();
            ChoosePO choosePO = chooseQuestionMap.get(Long.parseLong(chooseIdList.get(index)));
            chooseResponseDTO.setTitle(choosePO.getTitle());
            chooseResponseDTO.setChoose0(choosePO.getChoice0());
            chooseResponseDTO.setChoose1(choosePO.getChoice1());
            chooseResponseDTO.setChoose2(choosePO.getChoice2());
            chooseResponseDTO.setChoose3(choosePO.getChoice3());
            chooseResponseDTO.setKeyAnswer(choosePO.getKeyAnswer());
            chooseResponseDTO.setAnswer(chooseAnswerList[index]);
            chooseResponseDTO.setMark(choosePO.getKeyAnswer().equals(chooseAnswerList[index]) ? Constant.ONE : Constant.ZERO);
            chooseResponseDTOS.add(chooseResponseDTO);
        }
        List<ObjAnswerInfoResponseDTO.FillResponseDTO> fillResponseDTOS = new ArrayList<>(fillIdList.size());
        for (int index = 0; index < fillIdList.size(); index++) {
            ObjAnswerInfoResponseDTO.FillResponseDTO fillResponseDTO = new ObjAnswerInfoResponseDTO.FillResponseDTO();
            FillPO fillPO = fillQuestionMap.get(Long.parseLong(fillIdList.get(index)));
            fillResponseDTO.setTitle(fillPO.getTitle());
            fillResponseDTO.setKeyAnswer(fillPO.getKeyAnswer());
            fillResponseDTO.setAnswer(fillAnswerList[index]);
            fillResponseDTO.setMark(fillPO.getKeyAnswer().equals(fillAnswerList[index]) ? Constant.ONE : Constant.ZERO);
            fillResponseDTOS.add(fillResponseDTO);
        }
        ObjAnswerInfoResponseDTO responseDTO = new ObjAnswerInfoResponseDTO();
        responseDTO.setChooseList(chooseResponseDTOS);
        responseDTO.setFillList(fillResponseDTOS);
        responseDTO.setScore(studentPO.getObjGrade());
        StudentBaseInfoResponseDTO baseInfoDTO = new StudentBaseInfoResponseDTO();
        baseInfoDTO.setName(studentPO.getName());
        baseInfoDTO.setNumber(String.valueOf(studentPO.getNumber()));
        baseInfoDTO.setAdminClass(studentPO.getAdminClass());
        baseInfoDTO.setTeachClass(studentPO.getTeachClass());
        responseDTO.setBaseInfo(baseInfoDTO);
        return responseDTO;
    }

    /**
     * 教师查看学生客观题情况参数校验
     *
     * @param number 学生学号
     * @param prefix 业务信息前缀
     * @return 学生信息实体
     */
    private StudentPO verify(String number, String prefix) {
        Long userId = UserContextHolder.getUserId();
        LambdaQueryWrapper<TeacherPO> teacherQueryWrapper = Wrappers.<TeacherPO>lambdaQuery()
                .select(TeacherPO::getClassList)
                .eq(TeacherPO::getIsDelete, Boolean.FALSE)
                .eq(TeacherPO::getUserId, userId);
        String[] classList = teacherRepository.getOne(teacherQueryWrapper)
                .getClassList()
                .split("\\s", -1);
        LambdaQueryWrapper<StudentPO> studentInfoQueryWrapper = Wrappers.<StudentPO>lambdaQuery()
                .select(StudentPO::getId,StudentPO::getName, StudentPO::getNumber, StudentPO::getAdminClass, StudentPO::getTeachClass, StudentPO::getUserId, StudentPO::getObjGrade)
                .eq(StudentPO::getIsDelete, Boolean.FALSE)
                .eq(StudentPO::getNumber, number);
        StudentPO studentPO = studentRepository.getOne(studentInfoQueryWrapper);
        if (Objects.isNull(studentPO)) {
            log.info(prefix + ",学生不存在,number = {}", number);
            throw new BizException(BizExceptionCodeEnum.STUDENT_NOT_EXIST);
        }
        // 学生是否为所属教学班
        boolean belong = false;
        for (String teachClass : classList) {
            if (teachClass.equals(studentPO.getTeachClass())) {
                belong = true;
                break;
            }
        }
        if (!belong) {
            log.info("教师操作非所属教学班学生客观题答题情况,teacher userId = {}, student number = {}", userId, number);
            throw new BizException(BizExceptionCodeEnum.CLASS_NO_ACCESS);
        }
        return studentPO;
    }

    @Override
    public void modifyObjScore(ObjScoreModifyRequestDTO requestDTO) {
        String logPrefix = "客观题教师调分";
        //校验
        StudentPO studentPO = verify(requestDTO.getNumber(), logPrefix);
        // 修改客观题成绩
        LambdaUpdateWrapper<StudentPO> updateWrapper = Wrappers.<StudentPO>lambdaUpdate()
                .set(StudentPO::getObjGrade, requestDTO.getScore())
                .eq(StudentPO::getIsDelete, Boolean.FALSE)
                .eq(StudentPO::getId, studentPO.getId());
        studentRepository.update(updateWrapper);
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
