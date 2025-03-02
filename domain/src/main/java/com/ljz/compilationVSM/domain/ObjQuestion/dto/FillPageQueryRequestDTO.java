package com.ljz.compilationVSM.domain.ObjQuestion.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 填空题题库分页查询请求
 *
 * @author 劳金赞
 * @since 2025-03-02
 */
@Getter
@Setter
public class FillPageQueryRequestDTO {

    /**
     * 填空题题目，支持左右模糊匹配
     */
    private String title;

    /**
     * 页大小
     */
    private Integer pageSize;

    /**
     * 页号
     */
    private Integer pageIndex;
}
