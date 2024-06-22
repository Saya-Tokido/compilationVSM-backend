package com.ljz.compilationVSM.dependency.facade;

import com.ljz.compilationVSM.dependency.dto.CompilationInputDTO;
import com.ljz.compilationVSM.dependency.dto.CompilationOutputDTO;

public interface RemoteCompilerFacade {
    public CompilationOutputDTO compile(CompilationInputDTO compilationInputDTO);
}
