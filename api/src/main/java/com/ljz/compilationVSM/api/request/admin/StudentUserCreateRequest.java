package com.ljz.compilationVSM.api.request.admin;

import lombok.Getter;
import lombok.Setter;

/**
 * 学生用户创建请求
 *
 * @author ljz
 * @since 2025-01-20
 */
@Getter
@Setter
public class StudentUserCreateRequest {

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
