package com.ljz.compilationVSM.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ljz.compilationVSM.dto.CheckUnit;
import com.ljz.compilationVSM.entity.Choose;
import com.ljz.compilationVSM.dto.ChooseDto;

import java.util.List;

public interface ChooseService extends IService<Choose> {
    List<ChooseDto> getQuestion(int number);
    void checkAnswer(List<CheckUnit> checkBody);

}
