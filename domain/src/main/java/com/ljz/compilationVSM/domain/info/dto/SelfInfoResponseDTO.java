package com.ljz.compilationVSM.domain.info.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 当前用户基本信息响应
 *
 * @author ljz
 * @since 2025-02-07
 */
@Getter
@Setter
public class SelfInfoResponseDTO {

    /**
     * 姓名
     */
    private String name;

    /**
     * 编号（学号或工号）
     */
    private String number;
}
