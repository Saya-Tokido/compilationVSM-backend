package com.ljz.compilationVSM.domain.oj.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 源代码响应DTO
 *
 * @author ljz
 * @since 2025-01-16
 */
@Getter
@Setter
public class SourceCodeResponseDTO {
    /**
     * 源代码，每个字符串代表一行代码
     */
    List<String> code;
}
