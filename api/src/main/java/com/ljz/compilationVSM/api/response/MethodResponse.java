package com.ljz.compilationVSM.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 函数题描述
 * @author ljz
 * @since 2024-12-01
 */
@Data
@AllArgsConstructor
public class MethodResponse {
    /**
     * 题号id
     */
    private String id;

    /**
     * 函数标签
     */
    private String name;

    /**
     * 题目难度
     */
    private String level;

    /**
     * 提交次数
     */
    private String commitNum;

    /**
     * 通过百分比
     */
    private String passPercent;
}
