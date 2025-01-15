package com.ljz.compilationVSM.domain.ObjQuestion.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 客观题校验请求
 *
 * @author ljz
 * @since 2025-01-15
 */
@Setter
@Getter
public class ObjCheckRequestDTO {

    /**
     * 选择题作答情况
     */
    List<Answer> chooseAnswer;

    /**
     * 填空题作答情况
     */
    List<Answer> fillAnswer;

    /**
     * 客观题校验元素
     */
    @Data
    public static class Answer {

        /**
         * 题号id
         */
        private Long id;

        /**
         * 作答内容（选择题传入的是选项，填空题是内容）
         */
        private String answer;
    }
}
