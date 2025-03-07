package com.ljz.compilationVSM.domain.oj.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 词法分析器题分页响应DTO
 *
 * @author 劳金赞
 * @since 2025-03-07
 */
@Getter
@Setter
public class LexerTestcasePageResponseDTO {

    /**
     * 用例列表
     */
    private List<Testcase> list;

    /**
     * 总页数
     */
    private Integer totalPages;

    /**
     * 当前页号
     */
    private Integer currentPage;

    /**
     * 总记录数
     */
    private Integer totalRecords;

    /**
     * 用例类
     */
    @Getter
    @Setter
    public static class Testcase {

        /**
         * 用例id
         */
        private Long id;

        /**
         * 终端输入
         */
        private String terminalInput;

        /**
         * 终端输出
         */
        private String terminalOutput;
    }
}
