package com.ljz.compilationVSM.api.request.student;

import lombok.Data;

/**
 * 校验代码请求
 *
 * @author ljz
 * @since 2024-12-03
 */
@Data
public class CheckCodeRequest {

    /**
     * 题目id
     */
    private String problemId;

    /**
     * 待编译函数
     */
    private String code;
}
