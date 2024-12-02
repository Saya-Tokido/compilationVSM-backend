package com.ljz.compilationVSM.api.request;

import lombok.Data;

/**
 * 获取函数列表请求
 * @author ljz
 * @since 2024-12-01
 */
@Data
public class MethodListRequest {

    /**
     * 使用语言
     */
    private String language;

    /**
     * 待编译语言
     */
    private String compLanguage;
}
