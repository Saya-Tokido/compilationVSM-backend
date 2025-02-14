package com.ljz.compilationVSM.infrastructure.queryDTO;

import lombok.Getter;
import lombok.Setter;

/**
 * 词法分析器题查重代码查询请求参数
 *
 * @author ljz
 * @since 2025-02-13
 */
@Getter
@Setter
public class LexerPDCodeQueryDTO {

    /**
     * 教学班
     */
    private String teachClass;

    /**
     * 词法分析器id
     */
    private Long lexerId;

}
