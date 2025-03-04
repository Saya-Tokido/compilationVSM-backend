package com.ljz.compilationVSM.api.valid.rule;

import com.ljz.compilationVSM.api.valid.annotation.NotNull;

import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * 对象非空校验规则
 *
 * @author 劳金赞
 * @since 2025-03-04
 */
public class NotNullValidationRule implements ValidationRule {
    @Override
    public boolean isValid(Object value, Annotation annotation) {
        return Objects.nonNull(value);
    }

    @Override
    public String getErrorMessage(Annotation annotation) {
        NotNull notNull = (NotNull) annotation;
        return notNull.message();
    }
}