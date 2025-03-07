package com.ljz.compilationVSM.domain.oj.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 添加词法分析器题测试用例请求DTO
 *
 * @author 劳金赞
 * @since 2025-03-07
 */
@Getter
@Setter
public class LexerTestcaseAddRequestDTO {

    /**
     * 词法分析器题id
     */
    private Long lexerId;

    /**
     * 终端输入
     */
    private String terminalInput;

    /**
     * 终端输出
     */
    private String terminalOutput;
}
