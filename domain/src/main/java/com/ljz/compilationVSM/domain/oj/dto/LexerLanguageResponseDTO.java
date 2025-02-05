package com.ljz.compilationVSM.domain.oj.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 词法分析器可选语言响应DTO
 *
 * @author ljz
 * @since 2024-2-05
 */
@Getter
@Setter
public class LexerLanguageResponseDTO {

    /**
     * 可选语言映射列表
     */
    private List<LanguageMap> languageMaps;

    /**
     * 词法分析器待编译语言与可选编程语言列表映射
     */
    @Getter
    @Setter
    public static class LanguageMap{

        /**
         * 词法分析器编译语言
         */
        private String compLanguage;

        /**
         * 编程当前词法分析器可用的语言列表
         */
        private List<String> languageList;

        /**
         * 词法分析器题id
         */
        private String lexerId;
    }
}
