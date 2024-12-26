package com.ljz.compilationVSM.api.response.student;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 词法分析器可选语言
 *
 * @author ljz
 * @since 2024-12-05
 */
@Data
@AllArgsConstructor
public class LexerLanguageResponse {

    /**
     * 可选语言映射列表
     */
    private List<LanguageMap> languageMaps;

    /**
     * 词法分析器待编译语言与可选编程语言列表映射
     */
    @Data
    @AllArgsConstructor
    public static class LanguageMap{

        /**
         * 词法分析器编译语言
         */
        private String compLanguage;

        /**
         * 编程当前词法分析器可用的语言列表
         */
        private List<String> languageList;
    }
}
