package com.ljz.compilationVSM.domain.ObjQuestion.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 选择题题目创建请求
 *
 * @author 劳金赞
 * @since 2025-03-02
 */
@Getter
@Setter
public class ChooseAddRequestDTO {

    /**
     * 题目
     */
    private String title;

    /**
     * 选项0
     */
    private String choice0;

    /**
     * 选项1
     */
    private String choice1;

    /**
     * 选项2
     */
    private String choice2;

    /**
     * 选项3
     */
    private String choice3;

    /**
     * 标准答案
     */
    private String keyAnswer;
}
