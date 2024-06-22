package com.ljz.compilationVSM.domain.convert;

import com.ljz.compilationVSM.dependency.dto.AiOptimCodeDTO;
import com.ljz.compilationVSM.domain.aiQA.dto.OptimCodeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AiModelFacadeMapping {
    public AiOptimCodeDTO convert(OptimCodeDTO source);
}
