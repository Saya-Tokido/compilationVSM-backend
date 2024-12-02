package com.ljz.compilationVSM.domain.oj.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 函数体响应DTO
 * @author ljz
 * @since 2024-12-02
 */
@Data
@AllArgsConstructor
public class MethodBodyResponseDTO {

    /**
     * 函数体id
     */
    private String id;

    /**
     * 题目描述
     */
    private String description;

    /**
     * 输入
     */
    private String input;

    /**
     * 输出
     */
    private String output;

    /**
     * 入参
     */
    private String inParam;

    /**
     * 出参
     */
    private String outParam;

    /**
     * 初始函数体
     */
    private String body;
}
