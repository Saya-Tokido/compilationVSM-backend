package com.ljz.compilationVSM.domain.oj.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 词法分析器题保存请求参数DTO
 *
 * @author 劳金赞
 * @since 2025-03-06
 */
@Getter
@Setter
public class LexerSaveRequestDTO {

    /**
     * 词法分析器id,为空则代表新增
     */
    private Long id;

    /**
     * 编程语言
     */
    private String language;

    /**
     * 待编译语言
     */
    private String compLanguage;

    /**
     * 描述
     */
    private String description;

    /**
     * 示例终端输入
     */
    private String terminalInput;

    /**
     * 示例终端输出
     */
    private String terminalOutput;
}
