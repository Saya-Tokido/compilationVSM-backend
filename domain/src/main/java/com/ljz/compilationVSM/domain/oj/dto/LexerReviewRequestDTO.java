package com.ljz.compilationVSM.domain.oj.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 词法分析器题学生作答情况请求
 *
 * @author ljz
 * @since 2025-02-05
 */
@Getter
@Setter
public class LexerReviewRequestDTO {

    /**
     * 词法分析器题id
     */
    private String lexer_id;

    /**
     * 学生学号
     */
    private String number;
}
