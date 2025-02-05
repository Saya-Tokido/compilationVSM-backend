package com.ljz.compilationVSM.api.response.teacher;

import lombok.Getter;
import lombok.Setter;

/**
 * 词法分析器题示例响应
 *
 * @author ljz
 * @since 2025-02-04
 */
@Getter
@Setter
public class LexerDemoProblemResponse {

    /**
     * 题目描述
     */
    private String description;

    /**
     * 终端输入
     */
    private String terminalInput;

    /**
     * 终端输出
     */
    private String terminalOutput;
}
