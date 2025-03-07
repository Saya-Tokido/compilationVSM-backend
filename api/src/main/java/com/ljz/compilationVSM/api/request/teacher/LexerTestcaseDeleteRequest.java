package com.ljz.compilationVSM.api.request.teacher;

import com.ljz.compilationVSM.api.valid.annotation.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 删除词法分析器题用例请求
 *
 * @author 劳金赞
 * @since 2025-03-07
 */
@Getter
@Setter
public class LexerTestcaseDeleteRequest {

    /**
     * 词法分析器题用例id
     */
    @NotNull
    private Long id;
}
