package com.ljz.compilationVSM.api.request.teacher;

import com.ljz.compilationVSM.api.valid.annotation.NotBlank;
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
public class FillAddRequest {

    /**
     * 题目
     */
    @NotBlank(message = "题目不能为空")
    private String title;

    /**
     * 标准答案
     */
    @NotBlank(message = "标准答案不能为空")
    private String keyAnswer;
}
