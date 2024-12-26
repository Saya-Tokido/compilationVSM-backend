package com.ljz.compilationVSM.web.convert;

import com.ljz.compilationVSM.api.request.student.FreeQARequest;
import com.ljz.compilationVSM.api.request.student.OptimRequest;
import com.ljz.compilationVSM.api.request.student.SpecificQuestionRequest;
import com.ljz.compilationVSM.api.response.student.FreeQAResponse;
import com.ljz.compilationVSM.api.response.student.OptimResponse;
import com.ljz.compilationVSM.api.response.student.SpecificQuestionResponse;
import com.ljz.compilationVSM.common.dto.base.KeyValueDTO;
import com.ljz.compilationVSM.domain.aiQA.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.stream.Collectors;


@Mapper(componentModel = "spring",imports = {Collectors.class, KeyValueDTO.class})
public interface AiQAMapping {
    OptimCodeDTO optimConvert(OptimRequest source);
    OptimResponse optimConvert2(OptimizedDTO source);

    FreeQAQuestionDTO freeQAConvert(FreeQARequest source);
    FreeQAResponse freeQAConvert2(FreeQAAnswerDTO source);

    @Mapping(target = "questionKey",expression = "java(source.getKey())")
    SpecificQuestionDTO specificConvert(SpecificQuestionRequest source);
    SpecificQuestionResponse specificConvert2(SpecificAnswerDTO source);
}
