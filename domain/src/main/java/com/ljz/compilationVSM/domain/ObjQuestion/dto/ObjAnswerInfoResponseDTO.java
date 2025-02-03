package com.ljz.compilationVSM.domain.ObjQuestion.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 学生客观题作答详情DTO
 *
 * @author ljz
 * @since 2025-01-30
 */
@Getter
@Setter
public class ObjAnswerInfoResponseDTO {

    /**
     * 选择题作答列表
     */
    private List<ChooseResponseDTO> chooseList;

    /**
     * 填空题作答列表
     */
    private List<FillResponseDTO> fillList;

    /**
     * 客观题成绩
     */
    private Integer score;

    /**
     * 学生基本信息
     */
    StudentBaseInfoResponseDTO baseInfo;

    /**
     * 选择题信息
     */
    @Getter
    @Setter
    public static class ChooseResponseDTO{
        /**
         * 题目
         */
        private String title;

        /**
         * 选项0
         */
        private String choose0;

        /**
         * 选项1
         */
        private String choose1;

        /**
         * 选项2
         */
        private String choose2;

        /**
         * 选项3
         */
        private String choose3;

        /**
         * 学生答案
         */
        private String Answer;

        /**
         * 标准答案
         */
        private String keyAnswer;

        /**
         * 是否正确
         */
        private Integer mark;
    }

    /**
     * 填空题信息
     */
    @Getter
    @Setter
    public static class FillResponseDTO{

        /**
         * 题目
         */
        private String title;

        /**
         * 学生答案
         */
        private String Answer;

        /**
         * 标准答案
         */
        private String keyAnswer;

        /**
         * 是否正确
         */
        private Integer mark;
    }

}
