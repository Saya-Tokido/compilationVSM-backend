package com.ljz.compilationVSM.api.valid.rule;

import java.lang.annotation.Annotation;

/**
 * 校验规则接口
 *
 * @author 劳金赞
 * @since 2025-03-04
 */
public interface ValidationRule {

    /**
     * 校验逻辑
     *
     * @param value      待校验的值
     * @param annotation 校验类型注解
     * @return 校验结果
     */
    boolean isValid(Object value, Annotation annotation);

    /**
     * 获取异常反馈信息
     *
     * @param annotation 校验注解
     * @return 注解上的异常反馈信息
     */
    String getErrorMessage(Annotation annotation);
}