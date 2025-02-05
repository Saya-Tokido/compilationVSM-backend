package com.ljz.compilationVSM.domain.oj.dto;

import com.ljz.compilationVSM.domain.ObjQuestion.dto.StudentBaseInfoResponseDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * 词法分析器题代码评估响应
 *
 * @author ljz
 * @since 2025-02-05
 */
@Getter
@Setter
public class LexerCodeReviewResponseDTO {
    /**
     * 源代码
     */
    private SourceCodeResponseDTO sourceCode;

    /**
     * 得分
     */
    private Integer score;

    /**
     * 学生基本信息
     */
    private StudentBaseInfoResponseDTO baseInfo;
}
