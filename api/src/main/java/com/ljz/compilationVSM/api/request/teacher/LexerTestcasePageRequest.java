package com.ljz.compilationVSM.api.request.teacher;

import com.ljz.compilationVSM.api.base.PageRequest;
import com.ljz.compilationVSM.api.valid.annotation.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 词法分析器题用例分页查询请求
 *
 * @author 劳金赞
 * @since 2025-03-07
 */
@Getter
@Setter
public class LexerTestcasePageRequest extends PageRequest {

    /**
     * 词法分析器题id
     */
    @NotNull(message = "词法分析器题id不能为空")
    private Long lexerId;
}
