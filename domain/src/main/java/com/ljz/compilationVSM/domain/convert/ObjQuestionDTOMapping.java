package com.ljz.compilationVSM.domain.convert;

import com.ljz.compilationVSM.domain.ObjQuestion.dto.ChooseDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.FillDTO;
import com.ljz.compilationVSM.infrastructure.po.ChoosePO;
import com.ljz.compilationVSM.infrastructure.po.FillPO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ObjQuestionMapping {
    ChooseDTO chooseConvert(ChoosePO source);
    List<ChooseDTO> chooseListConvert(List<ChoosePO> source);

    FillDTO fillConvert(FillPO source);
    List<FillDTO> fillListConvert(List<FillPO> source);
}
