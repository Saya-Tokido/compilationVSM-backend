package com.ljz.compilationVSM.domain.oj.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 词法分析题题库分页查询请求DTO
 *
 * @author 劳金赞
 * @since 2025-03-04
 */
@Getter
@Setter
public class LexerPageQueryRequestDTO {

    /**
     * 编程语言
     */
    private String language;

    /**
     * 待编程语言
     */
    private String compLanguage;

    /**
     * 题目描述(全模糊)
     */
    private String description;

    /**
     * 页大小
     */
    private Integer pageSize;

    /**
     * 页号
     */
    private Integer pageIndex;
}
