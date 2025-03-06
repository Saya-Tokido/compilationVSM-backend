package com.ljz.compilationVSM.api.request.teacher;

import com.ljz.compilationVSM.api.base.PageRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * 词法分析题题库分页查询请求
 *
 * @author 劳金赞
 * @since 2025-03-04
 */
@Getter
@Setter
public class LexerPageQueryRequest extends PageRequest {

    /**
     * 编程语言
     */
    private String language;

    /**
     * 待编程语言
     */
    private String compLanguage;

    /**
     * 题目描述(全模糊)
     */
    private String description;
}
