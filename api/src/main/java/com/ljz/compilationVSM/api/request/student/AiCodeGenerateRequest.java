package com.ljz.compilationVSM.api.request.student;

import lombok.Getter;
import lombok.Setter;

/**
 * ai代码生成请求参数
 *
 * @author 劳金赞
 * @since 2025-03-07
 */
@Getter
@Setter
public class AiCodeGenerateRequest {

    /**
     * 学生未完成的代码
     */
    private String sourceCode;
}
