package com.ljz.compilationVSM.api.request.teacher;

import lombok.Getter;
import lombok.Setter;

/**
 * 词法分析器题代码抄袭学生信息导出请求
 *
 * @author ljz
 * @since 2025-02-15
 */
@Getter
@Setter
public class LexerPlaStudentExportRequest {
    /**
     * 教学班
     */
    private String teachClass;
}
