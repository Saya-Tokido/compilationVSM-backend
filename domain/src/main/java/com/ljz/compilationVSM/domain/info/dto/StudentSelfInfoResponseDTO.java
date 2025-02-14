package com.ljz.compilationVSM.domain.info.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 学生用户基本信息
 *
 * @author ljz
 * @since 2025-02-07
 */
@Getter
@Setter
public class StudentSelfInfoResponseDTO {

    /**
     * 基本信息
     */
    SelfInfoResponseDTO basicInfo;

    /**
     * 行政班
     */
    private String adminClass;

    /**
     * 教学班
     */
    private String teachClass;

    /**
     * 客观题成绩
     */
    private Integer objScore;

    /**
     * 词法分析器题成绩
     */
    private Integer lexerScore;
}
