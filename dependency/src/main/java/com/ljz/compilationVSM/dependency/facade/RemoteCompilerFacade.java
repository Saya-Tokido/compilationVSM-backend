package com.ljz.compilationVSM.dependency.facade;

import com.ljz.compilationVSM.dependency.dto.CompilationInputDTO;
import com.ljz.compilationVSM.dependency.dto.CompilationOutputDTO;

/**
 * 远程编译外观
 */
public interface RemoteCompilerFacade {

    /**
     * 调用远程编译器编译代码
     * 由于菜鸟编译接口存在爬虫反制，该接口已过期
     * @param compilationInputDTO 编译输入DTO
     * @return 编译输出结果DTO
     */
    @Deprecated
    CompilationOutputDTO compile(CompilationInputDTO compilationInputDTO);

    /**
     * 调用Runcode远程编译接口
     * @param compilationInputDTO 编译输入DTO
     * @return 编译输出结果DTO
     */
    CompilationOutputDTO compile2(CompilationInputDTO compilationInputDTO);

}
