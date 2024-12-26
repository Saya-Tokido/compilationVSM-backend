package com.ljz.compilationVSM.dependency.facade;

import com.ljz.compilationVSM.dependency.dto.CompilationInputDTO;
import com.ljz.compilationVSM.dependency.dto.CompilationOutputDTO;

/**
 * 远程编译外观
 */
public interface RemoteCompilerFacade {

    /**
     * 调用Runcode远程编译接口
     * @param compilationInputDTO 编译输入DTO
     * @return 编译输出结果DTO
     */
    CompilationOutputDTO compile2(CompilationInputDTO compilationInputDTO);

}
