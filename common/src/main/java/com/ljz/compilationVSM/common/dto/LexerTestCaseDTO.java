package com.ljz.compilationVSM.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 词法分析器测试用例序列化DTO
 *
 * @author ljz
 * @since 2025-01-17
 */
@Getter
@Setter
public class LexerTestCaseDTO {

    /**
     * 终端输入
     */
    private String terminalInput;

    /**
     * 终端输出
     */
    private String terminalOutput;
}
