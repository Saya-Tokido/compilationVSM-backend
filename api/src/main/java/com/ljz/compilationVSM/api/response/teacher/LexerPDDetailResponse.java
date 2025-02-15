package com.ljz.compilationVSM.api.response.teacher;

import lombok.Getter;
import lombok.Setter;

/**
 * 词法分析器题代码查重详情
 *
 * @author ljz
 * @since 2025-02-14
 */
@Getter
@Setter
@Deprecated
public class LexerPDDetailResponse {

    /**
     * 查重学生信息
     */
    private StudentBaseInfoResponse studentInfo;

    /**
     * 查重学生词法分析器题成绩
     */
    private Integer pdScore;

}
