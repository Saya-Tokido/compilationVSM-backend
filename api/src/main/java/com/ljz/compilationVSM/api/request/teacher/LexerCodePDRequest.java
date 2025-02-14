package com.ljz.compilationVSM.api.request.teacher;

import lombok.Getter;
import lombok.Setter;

/**
 * 词法分析器题查重请求
 *
 * @author ljz
 * @since 2025-02-13
 */
@Getter
@Setter
public class LexerCodePDRequest {
    /**
     * 教学班
     */
    private String teachClass;
}
