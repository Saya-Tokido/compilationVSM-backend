package com.ljz.compilationVSM.domain.ObjQuestion.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.CheckedChooseDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.CheckedFillDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.FillCheckDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.FillDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.service.FillService;
import com.ljz.compilationVSM.domain.convert.ObjQuestionDTOMapping;
import com.ljz.compilationVSM.infrastructure.mapper.FillMapper;
import com.ljz.compilationVSM.infrastructure.po.ChoosePO;
import com.ljz.compilationVSM.infrastructure.po.FillPO;
import com.ljz.compilationVSM.infrastructure.repository.FillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FillServiceImpl implements FillService {
    private FillMapper fillMapper;
    private ObjQuestionDTOMapping objQuestionDTOMapping;
    private FillRepository fillRepository;

    @Autowired
    FillServiceImpl(FillMapper fillMapper, ObjQuestionDTOMapping objQuestionDTOMapping,FillRepository fillRepository){
        this.fillMapper=fillMapper;
        this.objQuestionDTOMapping = objQuestionDTOMapping;
        this.fillRepository = fillRepository;
    }
    @Override
    public List<FillDTO> getFill(String type, Integer num) {
        //todo 暂未指定题目类型
        List<FillPO> fillList = fillMapper.getFill(num);
        return objQuestionDTOMapping.fillListConvert(fillList);
    }

    @Override
    public CheckedFillDTO checkFill(FillCheckDTO fillCheckDTO) {
        List<Long> idList = fillCheckDTO.getAnswers().stream()
                .map(item -> Long.parseLong(item.getId()))
                .collect(Collectors.toList());
        LambdaQueryWrapper<FillPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(FillPO::getId, FillPO::getKeyAnswer)
                .in(FillPO::getId, idList);
        Map<Long, String> answerMap = fillRepository.list(queryWrapper).stream()
                .collect(Collectors.toMap(FillPO::getId, FillPO::getKeyAnswer));
        List<CheckedFillDTO.CheckUnit> checkUnitList = fillCheckDTO.getAnswers().stream()
                .map(item -> {
                    Long id = Long.parseLong(item.getId());
                    String correctAnswer = answerMap.get(id);
                    boolean isCorrect = Objects.equals(item.getAnswer(), correctAnswer);
                    return new CheckedFillDTO.CheckUnit(item.getId(), correctAnswer, isCorrect);
                })
                .collect(Collectors.toList());
        return new CheckedFillDTO(checkUnitList);
    }
}
