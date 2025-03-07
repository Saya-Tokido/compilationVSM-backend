package com.ljz.compilationVSM.domain.oj.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 词法分析器题用例分页查询请求
 *
 * @author 劳金赞
 * @since 2025-03-07
 */
@Getter
@Setter
public class LexerTestcasePageRequestDTO {

    /**
     * 词法分析器题id
     */
    private Long lexerId;

    /**
     * 页大小
     */
    private Integer pageSize;

    /**
     * 页号
     */
    private Integer pageIndex;
}
