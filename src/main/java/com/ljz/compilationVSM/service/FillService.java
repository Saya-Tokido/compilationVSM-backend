package com.ljz.compilationVSM.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ljz.compilationVSM.dto.CheckUnit;
import com.ljz.compilationVSM.dto.FillDto;
import com.ljz.compilationVSM.entity.Fill;

import java.util.List;

public interface FillService extends IService<Fill> {
    List<FillDto> getQuestion(int number);

    void checkAnswer(List<CheckUnit> checkBody);
}
