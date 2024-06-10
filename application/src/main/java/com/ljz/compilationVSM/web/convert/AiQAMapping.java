package com.ljz.compilationVSM.web.convert;

import com.ljz.compilationVSM.api.request.FreeQARequest;
import com.ljz.compilationVSM.api.request.OptimRequest;
import com.ljz.compilationVSM.api.request.SpecificQuestionRequest;
import com.ljz.compilationVSM.api.response.FreeQAResponse;
import com.ljz.compilationVSM.api.response.OptimResponse;
import com.ljz.compilationVSM.api.response.SpecificQuestionResponse;
import com.ljz.compilationVSM.common.dto.base.KeyValueDTO;
import com.ljz.compilationVSM.domain.dto.*;
import com.ljz.compilationVSM.infrastructure.po.AiQAPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring",imports = {Collectors.class, KeyValueDTO.class})
public interface AiQAMapping {
    OptimCodeDTO optimConvert(OptimRequest source);
    OptimResponse optimConvert2(OptimizedDTO source);
//todo 修改字段名
    FreeQAQuestionDTO freeQAConvert(FreeQARequest source);
    FreeQAResponse freeQAConvert2(FreeQAAnswerDTO source);

    @Mapping(target = "questionKey",expression = "java(source.getKey())")
    SpecificQuestionDTO specificConvert(SpecificQuestionRequest source);
    SpecificQuestionResponse specificConvert2(SpecificAnswerDTO source);
}
