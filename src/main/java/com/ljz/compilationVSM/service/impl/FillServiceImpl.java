package com.ljz.compilationVSM.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljz.compilationVSM.dao.FillMapper;
import com.ljz.compilationVSM.entity.CheckUnit;
import com.ljz.compilationVSM.entity.Choose;
import com.ljz.compilationVSM.entity.Fill;
import com.ljz.compilationVSM.service.FillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FillServiceImpl extends ServiceImpl<FillMapper, Fill> implements FillService {

    @Autowired
    private FillMapper fillMapper;
    @Override
    public List<Fill> getQuestion(int number) {
        return fillMapper.getQuestion(number);
    }

    @Override
    public void checkAnswer(List<CheckUnit> checkBody) {
        for(CheckUnit checkUnit:checkBody){
            LambdaQueryWrapper<Fill> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.select(Fill::getKeyAnswer)
                    .eq(Fill::getId,checkUnit.getId());
            Fill fill=getOne(queryWrapper);
            if(null==checkUnit.getAnswer()||!checkUnit.getAnswer().equals(fill.getKeyAnswer())){
                checkUnit.setMark(false);
            }
            else{
                checkUnit.setMark(true);
            }
            checkUnit.setAnswer(fill.getKeyAnswer());
        }
    }
}
