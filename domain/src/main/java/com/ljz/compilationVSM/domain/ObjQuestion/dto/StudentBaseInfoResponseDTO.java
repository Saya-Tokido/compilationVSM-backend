package com.ljz.compilationVSM.domain.ObjQuestion.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 学生基本信息响应DTO
 *
 * @author ljz
 * @since 2025-01-30
 */
@Getter
@Setter
public class StudentBaseInfoResponseDTO {

    /**
     * 学生姓名
     */
    private String name;

    /**
     * 学生学号
     */
    private String number;

    /**
     * 行政班
     */
    private String adminClass;

    /**
     * 教学班
     */
    private String teachClass;
}
