package com.ljz.compilationVSM.api.response.teacher;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 填空题分页查询响应
 *
 * @author 劳金赞
 * @since 2025-03-02
 */
@Getter
@Setter
public class FillPageQueryResponse {

    /**
     * 填空题列表
     */
    List<Fill> fillList;

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
     * 填空题单元
     */
    @Getter
    @Setter
    public static class Fill {

        /**
         * 填空题id
         */
        private Long id;

        /**
         * 填空题题目
         */
        private String title;

        /**
         * 标准答案
         */
        private String keyAnswer;
    }
}
