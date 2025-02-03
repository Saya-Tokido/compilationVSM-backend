package com.ljz.compilationVSM.api.base;

import lombok.Getter;
import lombok.Setter;

/**
 * 分页查询通用请求
 *
 * @author ljz
 * @since 2025-01-27
 */
@Getter
@Setter
public class PageRequest {

    /**
     * 页大小
     */
    private Integer pageSize;

    /**
     * 页号
     */
    private Integer pageIndex;

    /**
     * 默认为起始页，页大小为10
     */
    public PageRequest() {
        pageIndex = 0;
        pageSize = 10;
    }
}
