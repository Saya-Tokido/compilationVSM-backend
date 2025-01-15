package com.ljz.compilationVSM.domain.ObjQuestion.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 客观题获取请求
 *
 * @author ljz
 * @since 2025-01-13
 */
@Getter
@Setter
public class ObjQueryDTO {

    /**
     * 选择题类型
     */
    private String chooseType;

    /**
     * 选择题数量
     */
    private Integer chooseNum;

    /**
     * 填空题类型
     */
    private String fillType;

    /**
     * 填空题数量
     */
    private Integer fillNum;
}
