package com.ljz.compilationVSM.domain.ObjQuestion.service;

import com.ljz.compilationVSM.domain.ObjQuestion.dto.CheckedFillDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.FillCheckDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.FillDTO;

import java.util.List;

/**
 * 填空题业务逻辑类
 */
public interface FillService {
    List<FillDTO> getFill(String type, Integer num);

    CheckedFillDTO checkFill(FillCheckDTO fillCheckDTO);
}
