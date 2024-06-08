package com.ljz.compilationVSM.domain.convert;

import com.ljz.compilationVSM.api.requst.OptimRequest;
import com.ljz.compilationVSM.api.response.OptimResponse;
import com.ljz.compilationVSM.domain.dto.OptimizedDTO;
import com.ljz.compilationVSM.domain.dto.OptimCodeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OptimCodeMapping {
    OptimCodeDTO convert(OptimRequest source);
    OptimResponse convert2(OptimizedDTO source);
}
