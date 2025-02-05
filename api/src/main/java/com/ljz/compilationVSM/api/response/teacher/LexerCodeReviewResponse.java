package com.ljz.compilationVSM.api.response.teacher;

import com.ljz.compilationVSM.api.request.common.SourceCodeResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * 词法分析器题代码评估响应
 *
 * @author ljz
 * @since 2025-02-04
 */
@Getter
@Setter
public class LexerCodeReviewResponse {
    /**
     * 源代码
     */
    private SourceCodeResponse sourceCode;

    /**
     * 得分
     */
    private Integer score;

    /**
     * 基本信息
     */
    private StudentBaseInfoResponse baseInfo;
}
