package com.ljz.compilationVSM.domain.ObjQuestion.service;

import com.ljz.compilationVSM.domain.ObjQuestion.dto.CheckedChooseDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.ChooseCheckDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.ChooseDTO;

import java.util.List;

/**
 * 选择题业务逻辑类
 */
public interface ChooseService {

     List<ChooseDTO> getChoose(String type, Integer num);

    CheckedChooseDTO checkChoose(ChooseCheckDTO chooseCheckDTO);
}
