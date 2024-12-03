package com.ljz.compilationVSM.api.request;

import lombok.Data;

/**
 * 校验函数体请求
 *
 * @author ljz
 * @since 2024-12-03
 */
@Data
public class CheckCodeRequest {

    /**
     * 待编译函数
     */
    private String code;
}
