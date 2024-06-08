package com.ljz.compilationVSM.dependency.convert;

import com.ljz.compilationVSM.api.requst.OptimRequest;
import com.ljz.compilationVSM.api.response.OptimResponse;
import com.ljz.compilationVSM.dependency.dto.AiOptimCodeRequestDTO;
import com.ljz.compilationVSM.domain.dto.OptimizedDTO;
import com.ljz.compilationVSM.domain.dto.OptimCodeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AiModelOptimCodeMapping {
    AiOptimCodeRequestDTO convert(OptimCodeDTO source);
}
