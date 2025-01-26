package com.ljz.compilationVSM.domain.account.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 学生用户创建DTO
 *
 * @author ljz
 * @since 2025-01-20
 */
@Getter
@Setter
public class StudentUserCreateRequestDTO {

    /**
     * 学号
     */
    private String number;

    /**
     * 学生姓名
     */
    private String name;

    /**
     * 行政班
     */
    private String adminClass;

    /**
     * 教学班
     */
    private String teachClass;

}
