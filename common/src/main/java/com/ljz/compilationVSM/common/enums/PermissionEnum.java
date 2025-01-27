package com.ljz.compilationVSM.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 权限枚举<br/>
 * 权限包括四位
 * 高位第一位第二位是业务名称<br/>
 * 低位第一第二位是对应业务的权限编号
 *
 * @author ljz
 * @since 2025-01-15
 */
@Getter
@AllArgsConstructor
public enum PermissionEnum {

    /**
     * 空权限，权限注解默认值
     */
    NULL("空权限","0"),


    /**
     * 客观题相关权限
     */
    OBJ_QUESTION_QUERY("获取客观题题目","1001"),
    OBJ_QUESTION_CHECK("客观题校验","1002"),

    /**
     * 词法分析器题相关权限
     */
    LEXER_LANGUAGE_QUERY("获取词法分析器语言","2001"),
    LEXER_DEMO_PROBLEM_QUERY("获取词法分析器示例","2002"),
    LEXER_LAST_COMMIT_CODE_QUERY("获取词法分析器最后一次提交代码","2003"),
    LEXER_CODE_CHECK("词法分析器代码校验","2004"),

    /**
     * 账户相关权限
     */
    RESET_STUDENT_ACCOUNT("重置学生账户","8001"),
    RESET_TEACHER_ACCOUNT("重置教师账户","8002"),
    CREATE_STUDENT_ACCOUNT("新增学生账户","8003"),
    CREATE_TEACHER_ACCOUNT("新增教师账户","8004");


    private final String name;
    private final String code;

}
