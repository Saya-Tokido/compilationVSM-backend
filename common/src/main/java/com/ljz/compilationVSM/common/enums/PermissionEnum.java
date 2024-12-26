package com.ljz.compilationVSM.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 权限枚举
 */
@Getter
@AllArgsConstructor
public enum PermissionEnum {

    /**
     * 空权限，权限注解默认值
     */
    NULL("空权限","0"),



    /**
     * 重置账户
     */
    RESET_STUDENT_ACCOUNT("重置学生账户","8001"),
    RESET_TEACHER_ACCOUNT("重置教师账户","8002");


    private final String name;
    private final String code;

}
