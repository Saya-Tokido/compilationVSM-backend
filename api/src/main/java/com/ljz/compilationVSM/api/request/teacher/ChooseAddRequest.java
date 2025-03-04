package com.ljz.compilationVSM.api.request.teacher;

import com.ljz.compilationVSM.api.valid.annotation.NotBlank;
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
public class ChooseAddRequest {

    /**
     * 题目
     */
    @NotBlank(message = "题目不能为空")
    private String title;

    /**
     * 选项0
     */
    @NotBlank(message = "要填满四个选项")
    private String choice0;

    /**
     * 选项1
     */
    @NotBlank(message = "要填满四个选项")
    private String choice1;

    /**
     * 选项2
     */
    @NotBlank(message = "要填满四个选项")
    private String choice2;

    /**
     * 选项3
     */
    @NotBlank(message = "要填满四个选项")
    private String choice3;

    /**
     * 标准答案
     */
    @NotBlank(message = "标准答案不能为空")
    private String keyAnswer;
}
