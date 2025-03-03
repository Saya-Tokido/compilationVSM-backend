package com.ljz.compilationVSM.domain.ObjQuestion.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 填空题题目创建请求
 *
 * @author 劳金赞
 * @since 2025-03-02
 */
@Getter
@Setter
public class FillAddRequestDTO {

    /**
     * 题目
     */
    private String title;

    /**
     * 标准答案
     */
    private String keyAnswer;
}
