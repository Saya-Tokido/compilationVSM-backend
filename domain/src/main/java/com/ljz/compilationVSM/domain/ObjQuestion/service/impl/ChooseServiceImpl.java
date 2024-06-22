package com.ljz.compilationVSM.domain.ObjQuestion.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.CheckedChooseDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.ChooseCheckDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.ChooseDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.service.ChooseService;
import com.ljz.compilationVSM.domain.convert.ObjQuestionDTOMapping;
import com.ljz.compilationVSM.infrastructure.mapper.ChooseMapper;
import com.ljz.compilationVSM.infrastructure.po.ChoosePO;
import com.ljz.compilationVSM.infrastructure.repository.ChooseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ChooseServiceImpl implements ChooseService {

    private ChooseMapper chooseMapper;
    private ObjQuestionDTOMapping objQuestionDTOMapping;
    private ChooseRepository chooseRepository;
    @Autowired
    ChooseServiceImpl(ChooseMapper chooseMapper, ObjQuestionDTOMapping objQuestionDTOMapping,ChooseRepository chooseRepository){
        this.chooseMapper=chooseMapper;
        this.objQuestionDTOMapping = objQuestionDTOMapping;
        this.chooseRepository = chooseRepository;
    }
    @Override
    public List<ChooseDTO> getChoose(String type, Integer num) {
        //todo 暂未指定题目类型
        List<ChoosePO> chooseList = chooseMapper.getChoose(num);
        return objQuestionDTOMapping.chooseListConvert(chooseList);
    }

    @Override
    public CheckedChooseDTO checkChoose(ChooseCheckDTO chooseCheckDTO) {
        List<Long> idList = chooseCheckDTO.getAnswers().stream()
                .map(item -> Long.parseLong(item.getId()))
                .collect(Collectors.toList());
        LambdaQueryWrapper<ChoosePO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(ChoosePO::getId, ChoosePO::getKeyAnswer)
                .in(ChoosePO::getId, idList);
        Map<Long, String> answerMap = chooseRepository.list(queryWrapper).stream()
                .collect(Collectors.toMap(ChoosePO::getId, ChoosePO::getKeyAnswer));
        List<CheckedChooseDTO.CheckUnit> checkUnitList = chooseCheckDTO.getAnswers().stream()
                .map(item -> {
                    Long id = Long.parseLong(item.getId());
                    String correctAnswer = answerMap.get(id);
                    boolean isCorrect = Objects.equals(item.getAnswer(), correctAnswer);
                    return new CheckedChooseDTO.CheckUnit(item.getId(), correctAnswer, isCorrect);
                })
                .collect(Collectors.toList());
        return new CheckedChooseDTO(checkUnitList);
    }
}
