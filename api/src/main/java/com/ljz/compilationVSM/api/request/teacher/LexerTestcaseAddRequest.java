package com.ljz.compilationVSM.api.request.teacher;

import com.ljz.compilationVSM.api.valid.annotation.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 添加词法分析器题测试用例请求
 *
 * @author 劳金赞
 * @since 2025-03-07
 */
@Getter
@Setter
public class LexerTestcaseAddRequest {

    /**
     * 词法分析器题id
     */
    @NotNull(message = "词法分析器题id不能为null")
    private Long lexerId;

    /**
     * 终端输入
     */
    private String terminalInput;

    /**
     * 终端输出
     */
    private String terminalOutput;
}
