package com.ljz.compilationVSM.api.valid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义校验注解，解决 @Valid切面前执行的问题
 *
 * @author 劳金赞
 * @since 2025-03-04
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Valid {
}