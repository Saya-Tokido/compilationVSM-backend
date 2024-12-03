package com.ljz.compilationVSM.dependency.dto;

import lombok.Data;

/**
 * 编译器结果DTO
 *
 * @author ljz
 * @since 2024-06-22
 */
@Data
public class CompilationOutputDTO {

    /**
     * 终端输出
     */
    String output;

    /**
     * True表示编译错误，False表示编译正常
     */
    Boolean compilationError;
}
