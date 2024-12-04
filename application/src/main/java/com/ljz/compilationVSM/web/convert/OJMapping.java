package com.ljz.compilationVSM.web.convert;

import com.ljz.compilationVSM.api.response.LexerProblemResponse;
import com.ljz.compilationVSM.api.response.MethodBodyResponse;
import com.ljz.compilationVSM.api.response.MethodResponse;
import com.ljz.compilationVSM.domain.oj.dto.LexerProblemResponseDTO;
import com.ljz.compilationVSM.domain.oj.dto.MethodBodyResponseDTO;
import com.ljz.compilationVSM.domain.oj.dto.MethodResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OJMapping {
    List<MethodResponse> methodResponseListConvert(List<MethodResponseDTO> source);

    MethodResponse methodResponseConvert(MethodResponseDTO source);

    MethodBodyResponse methodBodyResponseConvert(MethodBodyResponseDTO source);

    LexerProblemResponse lexerProblemResponseConvert(LexerProblemResponseDTO source);
}
