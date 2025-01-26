package com.ljz.compilationVSM.common.exception;

import lombok.AllArgsConstructor;

/**
 * 异常枚举
 *
 * @author ljz
 * @since 2024-12-25
 */
public enum BizExceptionCodeEnum implements BaseErrorInfoInterface {

    /**
     * 通用异常
     */
    SERVER_ERROR(500, "服务器内部错误"),
    PARAMETER_ERROR(1001, "请求参数错误"),


    /**
     * 用户异常
     */
    USER_NOT_EXIST_ERROR(2001, "用户不存在"),
    USERID_NOT_EXIST_ERROR(2002, "userId不存在"),
    USERNAME_OR_PASSWORD_ERROR(2003, "用户名或密码错误"),
    ILLEGAL_TOKEN_ERROR(2004, "非法token"),
    TOKEN_EXPIRED_ERROR(2005, "token已过期,请重新登录"),
    NULL_TOKEN_ERROR(2006, "token为空"),
    LOGIN_EXPIRED_ERROR(2007, "登录过期,请重新登录"),
    PERMISSION_FORBIDDEN_ERROR(2008, "无权限访问"),
    USER_NAME_EXISTED_ERROR(2009, "用户名已存在"),

    /**
     * 客观题异常
     */
    ILLEGAL_OBJ_QUESTION_ID_ERROR(3001,"非法客观题Id"),

    /**
     * 词法分析器题异常
     */
    LEXER_PROBLEM_NOT_FOUNT(4001,"词法分析器题目不存在"),
    LEXER_TESTCASE_NOT_FOUNT(4002,"词法分析器用例不存在"),
    LEXER_EXAM_CLOSED(4003,"词法分析器题停止提交");

    /**
     * 错误码
     */
    private final Integer resultCode;

    /**
     * 错误描述
     */
    private final String resultMsg;

    BizExceptionCodeEnum(Integer resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    @Override
    public Integer getResultCode() {
        return this.resultCode;
    }

    @Override
    public String getResultMsg() {
        return this.resultMsg;
    }
}
