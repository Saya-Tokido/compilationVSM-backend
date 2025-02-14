package com.ljz.compilationVSM.web.convert.teacher;

import com.ljz.compilationVSM.api.request.common.SourceCodeResponse;
import com.ljz.compilationVSM.api.request.teacher.LexerReviewRequest;
import com.ljz.compilationVSM.api.response.common.LexerLanguageResponse;
import com.ljz.compilationVSM.api.response.teacher.LexerCodeReviewResponse;
import com.ljz.compilationVSM.api.response.teacher.LexerDemoProblemResponse;
import com.ljz.compilationVSM.api.response.teacher.LexerPDInfoResponse;
import com.ljz.compilationVSM.api.response.teacher.StudentBaseInfoResponse;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.StudentBaseInfoResponseDTO;
import com.ljz.compilationVSM.domain.oj.dto.*;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 编程题接口对象映射
 *
 * @author ljz
 * @since 2025-02-05
 */
@Mapper(componentModel = "spring", imports = {List.class})
public interface OJReviewMapping {

    LexerLanguageResponse convert(LexerLanguageResponseDTO source);

    LexerLanguageResponse.LanguageMap convert(LexerLanguageResponseDTO.LanguageMap source);

    LexerDemoProblemResponse convert(LexerProblemResponseDTO source);

    LexerReviewRequestDTO convert(LexerReviewRequest source);

    LexerCodeReviewResponse convert(LexerCodeReviewResponseDTO source);

    SourceCodeResponse convert(SourceCodeResponseDTO source);

    StudentBaseInfoResponse convert(StudentBaseInfoResponseDTO source);

    LexerPDInfoResponse convert(LexerPDInfoResponseDTO source);

    LexerPDInfoResponse.PDInfo convert(LexerPDInfoResponseDTO.PDInfo source);

}
