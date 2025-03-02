package com.ljz.compilationVSM.api.request.teacher;

import com.ljz.compilationVSM.api.base.PageRequest;
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
public class FillPageQueryRequest extends PageRequest {

    /**
     * 填空题题目，支持左右模糊匹配
     */
    private String title;
}
