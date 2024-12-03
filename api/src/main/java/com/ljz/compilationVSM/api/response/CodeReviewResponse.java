package com.ljz.compilationVSM.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 代码评估结果
 *
 * @author ljz
 * @since 2024-12-02
 */
@AllArgsConstructor
@Data
public class CodeReviewResponse {

    /**
     * 成功状态
     */
    Integer status;

    /**
     * 错误信息
     */
    String message;
}
