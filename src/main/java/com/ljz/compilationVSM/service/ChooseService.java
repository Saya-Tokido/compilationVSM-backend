package com.ljz.compilationVSM.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ljz.compilationVSM.entity.CheckUnit;
import com.ljz.compilationVSM.entity.Choose;
import com.ljz.compilationVSM.entity.ChooseBody;

import java.util.List;
import java.util.Map;

public interface ChooseService extends IService<Choose> {
    List<ChooseBody> getQuestion(int number);
    void checkAnswer(List<CheckUnit> checkBody);

}
