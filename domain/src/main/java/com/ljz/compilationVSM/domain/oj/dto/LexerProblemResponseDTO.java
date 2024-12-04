package com.ljz.compilationVSM.domain.oj.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 词法分析器题目响应DTO
 *
 * @author ljz
 * @since 2024-12-03
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class LexerProblemResponseDTO {

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
