package com.ljz.compilationVSM.api.response.admin;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 学生信息分页响应
 *
 * @author ljz
 * @since 2025-02-16
 */
@Getter
@Setter
public class StudentInfoPageResponse {

    /**
     * 学生信息列表
     */
    List<StudentInfo> studentInfoList;

    /**
     * 所有教学班
     */
    List<String> classList;

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
     * 学生信息类
     */
    @Getter
    @Setter
    public static class StudentInfo{

        /**
         * 学生学号
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
}
