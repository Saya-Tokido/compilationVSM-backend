package com.ljz.compilationVSM.api.request.teacher;

import com.ljz.compilationVSM.api.base.PageRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 分页查询学生信息请求
 *
 * @author ljz
 * @since 2025-01-27
 */
@Getter
@Setter
public class StudentPageQueryRequest extends PageRequest {
    /**
     * 教学班列表
     */
    private List<String> classList;

    /**
     * 学生姓名,支持右模糊匹配
     */
    private String stuName;

    /**
     * 学生学号
     */
    private String number;

    /**
     * 按客观题成绩升序
     */
    private Integer objAsc;

    /**
     * 按词法分析器题成绩升序
     */
    private Integer lexerAsc;

}
