package com.ljz.compilationVSM.api.response.teacher;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 词法分析器题分页查询响应
 *
 * @author 劳金赞
 * @since 2025-03-04
 */
@Getter
@Setter
public class LexerPageQueryResponse {

    /**
     * 词法分析器题列表
     */
    List<Lexer> lexerList;

    /**
     * 总页数
     */
    Integer totalPages;

    /**
     * 当前页号
     */
    Integer currentPage;

    /**
     * 总记录数
     */
    Integer totalRecords;

    /**
     * 词法分析器题单元
     */
    @Getter
    @Setter
    public static class Lexer {

        /**
         * 主键id
         */
        private Long id;

        /**
         * 编程语言
         */
        private String language;

        /**
         * 待编程语言
         */
        private String compLanguage;

        /**
         * 题目描述
         */
        private String description;

        /**
         * 提交次数
         */
        private Long commitNum;

        /**
         * 通过次数
         */
        private Long passNum;
    }
}
