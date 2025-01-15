package com.ljz.compilationVSM.web.convert;

import com.ljz.compilationVSM.api.request.student.ChooseCheckRequest;
import com.ljz.compilationVSM.api.request.student.FillCheckRequest;
import com.ljz.compilationVSM.api.request.student.ObjCheckRequest;
import com.ljz.compilationVSM.api.request.student.ObjRequest;
import com.ljz.compilationVSM.api.response.student.*;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ObjQuestionMapping {

    ObjQueryDTO ObjQueryConvert(ObjRequest source);

    ObjResponse objResponseConvert(ObjResponseDTO source);

    ChooseResponse chooseResponseConvert(ChooseDTO source);

    FillResponse fillResponseConvert(FillDTO source);

    ObjCheckRequestDTO objCheckRequestDTOConvert(ObjCheckRequest source);

    ObjCheckResponse objCheckResponseConvert(ObjCheckResponseDTO source);

}
