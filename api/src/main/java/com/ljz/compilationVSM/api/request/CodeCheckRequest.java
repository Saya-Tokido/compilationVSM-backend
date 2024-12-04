package com.ljz.compilationVSM.api.request;

import lombok.Getter;

/**
 * 代码校验请求
 *
 * @author ljz
 * @since 2024-12-04
 */
@Getter
public class CodeCheckRequest {

    /**
     * 题目id
     */
    private String problemId;

    /**
     * 待校验的代码
     */
    private String code;
}
