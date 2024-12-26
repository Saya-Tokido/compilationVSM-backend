package com.ljz.compilationVSM.web.convert;

import com.ljz.compilationVSM.api.request.student.ChooseCheckRequest;
import com.ljz.compilationVSM.api.request.student.FillCheckRequest;
import com.ljz.compilationVSM.api.response.student.ChooseCheckResponse;
import com.ljz.compilationVSM.api.response.student.ChooseResponse;
import com.ljz.compilationVSM.api.response.student.FillCheckResponse;
import com.ljz.compilationVSM.api.response.student.FillResponse;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ObjQuestionMapping {

    ChooseResponse chooseConvert(ChooseDTO source);
    List<ChooseResponse> chooseListConvert(List<ChooseDTO> source);

    FillResponse fillConvert(FillDTO source);
    List<FillResponse> fillListConvert(List<FillDTO> source);

    ChooseCheckDTO.Answer chooseCheckAnswerConvert(ChooseCheckRequest.Answer source);
    ChooseCheckDTO chooseCheckDTOConvert(ChooseCheckRequest source);

    FillCheckDTO.Answer fillCheckAnswerConvert(FillCheckRequest.Answer source);
    FillCheckDTO fillCheckDTOConvert(FillCheckRequest source);

    ChooseCheckResponse.CheckUnit chooseCheckUnitConvert(CheckedChooseDTO.CheckUnit source);
    ChooseCheckResponse chooseCheckedConvert(CheckedChooseDTO source);

    FillCheckResponse.CheckUnit fillCheckUnitConvert(CheckedFillDTO.CheckUnit source);
    FillCheckResponse fillCheckedConvert(CheckedFillDTO source);
}
