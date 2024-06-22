package com.ljz.compilationVSM.domain.convert;

import com.ljz.compilationVSM.dependency.dto.CompilationInputDTO;
import com.ljz.compilationVSM.domain.experiment.dto.CodeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompilationMapping {
    CompilationInputDTO convert(CodeDTO source);
}
