package com.ljz.compilationVSM.domain.convert;

import com.ljz.compilationVSM.common.dto.base.KeyValueDTO;
import com.ljz.compilationVSM.infrastructure.po.AiQAPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",imports = {Collectors.class,KeyValueDTO.class})
public interface QuestionListMapping {

    @Mappings({
            @Mapping(target = "key",expression = "java(source.getId().toString())"),
            @Mapping(target = "value",expression = "java(source.getQuestion())")
    })
    KeyValueDTO<String,String> aiQAPO2keyValueDTO(AiQAPO source);

    List<KeyValueDTO<String, String>> convert(List<AiQAPO> source);

}
