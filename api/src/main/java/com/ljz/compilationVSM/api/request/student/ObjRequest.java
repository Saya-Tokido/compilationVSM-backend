package com.ljz.compilationVSM.api.request.student;

import lombok.Getter;
import lombok.Setter;

/**
 * 客观题获取请求
 *
 * @author ljz
 * @since 2024-12-28
 */
@Getter
@Setter
public class ObjRequest {

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
