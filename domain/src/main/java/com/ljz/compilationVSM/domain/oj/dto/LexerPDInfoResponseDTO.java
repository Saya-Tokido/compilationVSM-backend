package com.ljz.compilationVSM.domain.oj.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 词法分析器查重率信息响应DTO
 *
 * @author ljz
 * @since 2025-02-11
 */
@Getter
@Setter
public class LexerPDInfoResponseDTO {

    /**
     * 各教学班查重信息
     */
    private List<PDInfo> pdInfoList;

    /**
     * 待编译语言
     */
    private String compLanguage;

    /**
     * 查重信息类
     */
    @Getter
    @Setter
    public static class PDInfo {
        /**
         * 教学班
         */
        private String teachClass;

        /**
         * 高查重率代码个数
         */
        private Integer plagiarismNum;
    }

}
