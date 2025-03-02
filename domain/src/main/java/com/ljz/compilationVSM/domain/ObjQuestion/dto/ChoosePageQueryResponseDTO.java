package com.ljz.compilationVSM.domain.ObjQuestion.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 选择题分页查询响应
 *
 * @author 劳金赞
 * @since 2025-03-02
 */
@Getter
@Setter
public class ChoosePageQueryResponseDTO {

    /**
     * 选择题列表
     */
    List<Choose> chooseList;

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
     * 选择题单元
     */
    @Getter
    @Setter
    public static class Choose{

        /**
         * 选择题id
         */
        private Long id;

        /**
         * 选择题题目
         */
        private String title;

        /**
         * 选项0
         */
        private String choice0;

        /**
         * 选项1
         */
        private String choice1;

        /**
         * 选项2
         */
        private String choice2;

        /**
         * 选项3
         */
        private String choice3;

        /**
         * 标准答案
         */
        private String keyAnswer;
    }
}
