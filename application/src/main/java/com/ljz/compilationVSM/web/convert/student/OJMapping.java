package com.ljz.compilationVSM.web.convert.student;

import com.ljz.compilationVSM.api.request.common.SourceCodeResponse;
import com.ljz.compilationVSM.api.request.student.AiCodeGenerateRequest;
import com.ljz.compilationVSM.api.response.student.LexerProblemResponse;
import com.ljz.compilationVSM.api.response.student.MethodBodyResponse;
import com.ljz.compilationVSM.api.response.student.MethodResponse;
import com.ljz.compilationVSM.domain.oj.dto.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", imports = {List.class})
public interface OJMapping {
    List<MethodResponse> methodResponseListConvert(List<MethodResponseDTO> source);

    MethodResponse methodResponseConvert(MethodResponseDTO source);

    MethodBodyResponse methodBodyResponseConvert(MethodBodyResponseDTO source);

    LexerProblemResponse lexerProblemResponseConvert(LexerProblemResponseDTO source);

    SourceCodeResponse sourceCodeResponseConvert(SourceCodeResponseDTO source);

    AiCodeGenerateRequestDTO convert(AiCodeGenerateRequest source);

}
