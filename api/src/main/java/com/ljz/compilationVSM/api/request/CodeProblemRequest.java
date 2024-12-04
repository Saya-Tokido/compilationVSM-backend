package com.ljz.compilationVSM.api.request;

import lombok.Getter;

/**
 * 代码编译体请求
 *
 * @author ljz
 * @since 2024-12-04
 */
@Getter
public class CodeProblemRequest {

    /**
     * 编程代码
     */
    private String language;

    /**
     * 待编译代码
     */
    private String compLanguage;
}
