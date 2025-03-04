package com.ljz.compilationVSM.api.valid.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对象非空校验注解
 *
 * @author 劳金赞
 * @since 2025-03-04
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNull {
    String message() default "字段不能为 null";
}