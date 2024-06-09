package com.ljz.compilationVSM.dependency.convert;

import com.ljz.compilationVSM.dependency.dto.AiOptimCodeRequestDTO;
import com.ljz.compilationVSM.domain.dto.OptimCodeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AiModelOptimCodeMapping {
    AiOptimCodeRequestDTO convert(OptimCodeDTO source);
}
