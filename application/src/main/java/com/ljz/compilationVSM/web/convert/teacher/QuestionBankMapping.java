package com.ljz.compilationVSM.web.convert.teacher;

import com.ljz.compilationVSM.api.request.teacher.ChooseAddRequest;
import com.ljz.compilationVSM.api.request.teacher.ChoosePageQueryRequest;
import com.ljz.compilationVSM.api.request.teacher.FillAddRequest;
import com.ljz.compilationVSM.api.request.teacher.FillPageQueryRequest;
import com.ljz.compilationVSM.api.response.teacher.ChoosePageQueryResponse;
import com.ljz.compilationVSM.api.response.teacher.FillPageQueryResponse;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.*;
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
}
