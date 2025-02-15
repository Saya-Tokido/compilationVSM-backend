package com.ljz.compilationVSM.api.response.teacher;

import com.ljz.compilationVSM.api.request.common.SourceCodeResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * 词法分析器题代码对比响应
 *
 * @author ljz
 * @since 2025-02-14
 */
@Getter
@Setter
@Deprecated
public class LexerCodeCompResponse {

    /**
     * 查重学生代码
     */
    private SourceCodeResponse pdSourceCode;

    /**
     * 参照学生信息
     */
    private SourceCodeResponse compSourceCode;

}
