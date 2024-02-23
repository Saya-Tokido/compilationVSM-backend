package com.ljz.compilationVSM.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljz.compilationVSM.dao.ChooseMapper;
import com.ljz.compilationVSM.dto.CheckUnit;
import com.ljz.compilationVSM.entity.Choose;
import com.ljz.compilationVSM.dto.ChooseDto;
import com.ljz.compilationVSM.service.ChooseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j

@Service
public class ChooseServiceImpl extends ServiceImpl<ChooseMapper, Choose> implements ChooseService {

    @Autowired
    private ChooseMapper chooseMapper;
    @Override
    public List<ChooseDto> getQuestion(int number) {
        List<Choose> chooseList = chooseMapper.getQuestion(number);
        List<ChooseDto> bodyList=new ArrayList<>();
        chooseList.stream().forEach(item -> {
            ChooseDto chooseBody=new ChooseDto();
            chooseBody.setId(item.getId());
            chooseBody.setQuestion(item.getTitle());
            List<String> choiceList=new ArrayList<>();
            choiceList.add(item.getChoice0());
            choiceList.add(item.getChoice1());
            choiceList.add(item.getChoice2());
            choiceList.add(item.getChoice3());
            chooseBody.setChoiceList(choiceList);
            bodyList.add(chooseBody);
        });
        return bodyList;
    }

    @Override
    public void checkAnswer(List<CheckUnit> checkBody) {
        for(CheckUnit checkUnit:checkBody){
            LambdaQueryWrapper<Choose> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.select(Choose::getKeyAnswer)
                    .eq(Choose::getId,checkUnit.getId());
            Choose choose=getOne(queryWrapper);
            if(null==checkUnit.getAnswer()||!checkUnit.getAnswer().equals(choose.getKeyAnswer())){
                checkUnit.setMark(false);
            }
            else{
                checkUnit.setMark(true);
            }
            checkUnit.setAnswer(choose.getKeyAnswer());
        }
    }

}
