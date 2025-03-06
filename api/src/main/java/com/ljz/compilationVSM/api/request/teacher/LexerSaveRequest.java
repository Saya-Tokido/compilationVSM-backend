package com.ljz.compilationVSM.api.request.teacher;

import com.ljz.compilationVSM.api.valid.annotation.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 词法分析器题保存请求参数
 *
 * @author 劳金赞
 * @since 2025-03-06
 */
@Getter
@Setter
public class LexerSaveRequest {

    /**
     * 词法分析器id,为空则代表新增
     */
    private Long id;

    /**
     * 编程语言
     */
    @NotBlank
    private String language;

    /**
     * 待编译语言
     */
    @NotBlank
    private String compLanguage;

    /**
     * 描述
     */
    private String description;

    /**
     * 示例终端输入
     */
    @NotBlank
    private String terminalInput;

    /**
     * 示例终端输出
     */
    @NotBlank
    private String terminalOutput;
}
