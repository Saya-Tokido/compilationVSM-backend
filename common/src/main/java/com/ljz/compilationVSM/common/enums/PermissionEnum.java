package com.ljz.compilationVSM.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 权限枚举<br/>
 * 高位对应业务名称编号<br/>
 * 低位三位对应业务的权限编号
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
    OBJ_REVIEW("评阅客观题答题情况","1011"),
    OBJ_SCORE_MODIFY("客观题调分","1012"),
    PAGE_CHOOSE_BANK("选择题题库分页查询","1013"),
    PAGE_FILL_BANK("填空题题库分页查询","1014"),
    CHOOSE_DELETE("选择题删除","1015"),
    FILL_DELETE("填空题删除","1016"),

    /**
     * 词法分析器题相关权限
     */
    LEXER_LANGUAGE_QUERY_EXAM("测验时获取词法分析器语言","2001"),
    LEXER_DEMO_PROBLEM_QUERY_EXAM("测验时获取词法分析器示例","2002"),
    LEXER_LAST_COMMIT_CODE_QUERY("获取词法分析器最后一次提交代码","2003"),
    LEXER_CODE_CHECK("词法分析器代码校验","2004"),
    LEXER_LANGUAGE_QUERY_REVIEW("评估时获取词法分析器语言","2011"),
    LEXER_DEMO_PROBLEM_QUERY_REVIEW("评估时获取词法分析器题目","2012"),
    LEXER_CODE_REVIEW("评估词法分析器题代码","2013"),
    LEXER_CODE_PD_INFO_QUERY("词法分析器题代码查重信息获取","2014"),
    LEXER_CODE_PD_EXECUTE("执行词法分析器题代码查重","2015"),
    LEXER_PD_STUDENT_EXPORT("词法分析器题抄袭学生数据导出","2016"),

    /**
     * 账户相关权限
     */
    RESET_STUDENT_ACCOUNT("重置学生账户","8001"),
    RESET_TEACHER_ACCOUNT("重置教师账户","8002"),
    CREATE_STUDENT_ACCOUNT("新增学生账户","8003"),
    CREATE_TEACHER_ACCOUNT("新增教师账户","8004"),
    CREATE_STUDENT_ACCOUNT_BY_EXCEL("通过Excel添加学生账户","8005"),
    DELETE_STUDENT_ACCOUNT("删除学生账户","8006"),
    DELETE_TEACHER_ACCOUNT("删除教师账户","8007"),

    /**
     * 基本信息相关权限
     */
    PAGE_QUERY_STUDENT_INFO("分页查询学生基本信息","9001"),
    TEACHER_SELF_INFO_QUERY("教师用户信息查询","9002"),
    STUDENT_SELF_INFO_QUERY("学生用户信息查询","9003"),
    ADMIN_PAGE_QUERY_STUDENT("管理员分页查询学生信息","9010");

    private final String name;
    private final String code;

}
