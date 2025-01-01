package com.ljz.compilationVSM.api.response.student;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 客观题校验反馈
 *
 * @author ljz
 * @since 2024-12-28
 */
@Setter
@Getter
public class ObjCheckResponse {

    /**
     * 选择题校验结果
     */
    private List<ResultUnit> chooseResultList;

    /**
     * 填空题校验结果
     */
    private List<ResultUnit> fillResultList;

    /**
     * 分数
     */
    private Integer score;

    /**
     * 校验结果单元
     */
    @Data
    public static class ResultUnit{

        /**
         * 作答内容 (选择题是编号,如0,1,2,3)
         */
        String answer;

        /**
         * 正确情况 (0是错误,1正确)
         */
        Integer mark;
    }
}
