package com.ljz.compilationVSM.api.request.admin;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 教师用户创建请求
 *
 * @author ljz
 * @since 2025-01-27
 */
@Getter
@Setter
public class TeacherUserCreateRequest {

    /**
     * 工号
     */
    private String number;

    /**
     * 老师姓名
     */
    private String name;

    /**
     * 所带的所有教学班
     */
    private List<String> teachClass;

}
