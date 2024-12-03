package com.ljz.compilationVSM.domain.oj.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 代码评估结果
 *
 * @author ljz
 * @since 2024-12-02
 */
@AllArgsConstructor
@Getter
public class CodeReviewResponseDTO {

    /**
     * 成功状态
     */
    Integer status;

    /**
     * 错误信息
     */
    String message;
}
