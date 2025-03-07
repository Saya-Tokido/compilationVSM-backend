package com.ljz.compilationVSM.web.convert.teacher;

import com.ljz.compilationVSM.api.request.teacher.*;
import com.ljz.compilationVSM.api.response.teacher.ChoosePageQueryResponse;
import com.ljz.compilationVSM.api.response.teacher.FillPageQueryResponse;
import com.ljz.compilationVSM.api.response.teacher.LexerDetailResponse;
import com.ljz.compilationVSM.api.response.teacher.LexerPageQueryResponse;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.*;
import com.ljz.compilationVSM.domain.oj.dto.LexerDetailResponseDTO;
import com.ljz.compilationVSM.domain.oj.dto.LexerPageQueryRequestDTO;
import com.ljz.compilationVSM.domain.oj.dto.LexerPageQueryResponseDTO;
import com.ljz.compilationVSM.domain.oj.dto.LexerSaveRequestDTO;
import org.mapstruct.Mapper;

/**
 * 题库对象映射器
 *
 * @author 劳金赞
 * @since 2025-03-02
 */
@Mapper(componentModel = "spring")
public interface QuestionBankMapping {
    ChoosePageQueryRequestDTO convert(ChoosePageQueryRequest source);

    FillPageQueryRequestDTO convert(FillPageQueryRequest source);

    ChoosePageQueryResponse convert(ChoosePageQueryResponseDTO source);

    FillPageQueryResponse convert(FillPageQueryResponseDTO source);

    ChooseAddRequestDTO convert(ChooseAddRequest source);

    FillAddRequestDTO convert(FillAddRequest source);

    LexerPageQueryRequestDTO convert(LexerPageQueryRequest source);

    LexerPageQueryResponse convert(LexerPageQueryResponseDTO source);

    LexerSaveRequestDTO convert(LexerSaveRequest source);

    LexerDetailResponse convert(LexerDetailResponseDTO source);
}
