package com.ljz.compilationVSM.api.request.base;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 源代码响应
 *
 * @author ljz
 * @since 2025-01-16
 */
@Getter
@Setter
public class SourceCodeResponse {
    /**
     * 源代码，每个字符串代表一行代码
     */
    List<String> code;
}
