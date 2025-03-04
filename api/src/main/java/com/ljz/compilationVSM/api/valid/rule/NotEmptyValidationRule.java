package com.ljz.compilationVSM.api.valid.rule;

import com.ljz.compilationVSM.api.valid.annotation.NotEmpty;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Objects;

/**
 * 集合非空校验规则
 *
 * @author 劳金赞
 * @since 2025-03-04
 */
public class NotEmptyValidationRule implements ValidationRule {
    @Override
    public boolean isValid(Object value, Annotation annotation) {
        if (Objects.isNull(value)) {
            return false;
        }
        if (value instanceof Collection<?> collection) {
            return !CollectionUtils.isEmpty(collection);
        }
        return false;
    }

    @Override
    public String getErrorMessage(Annotation annotation) {
        NotEmpty notNull = (NotEmpty) annotation;
        return notNull.message();
    }
}