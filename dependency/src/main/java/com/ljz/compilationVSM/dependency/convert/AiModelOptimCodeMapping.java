package com.ljz.compilationVSM.dependency.convert;

import com.ljz.compilationVSM.dependency.dto.AiOptimCodeDTO;
import com.ljz.compilationVSM.dependency.dto.AiOptimCodeRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AiModelOptimCodeMapping {
    AiOptimCodeRequestDTO convert(AiOptimCodeDTO source);
}
