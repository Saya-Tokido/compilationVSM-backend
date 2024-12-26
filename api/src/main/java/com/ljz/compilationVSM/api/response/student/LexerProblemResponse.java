package com.ljz.compilationVSM.api.response.student;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 词法分析器题目响应
 *
 * @author ljz
 * @since 2024-12-03
 */
@Data
@AllArgsConstructor
public class LexerProblemResponse {

    /**
     * 词法分析器题目id
     */
    private String lexerId;

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
