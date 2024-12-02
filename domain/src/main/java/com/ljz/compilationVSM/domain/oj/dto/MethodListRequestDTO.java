package com.ljz.compilationVSM.domain.oj.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 获取函数列表请求DTO
 * @author ljz
 * @since 2024-12-01
 */
@Data
@AllArgsConstructor
public class MethodListRequestDTO {
    /**
     * 使用语言
     */
    private String language;

    /**
     * 待编译语言
     */
    private String compLanguage;
}
