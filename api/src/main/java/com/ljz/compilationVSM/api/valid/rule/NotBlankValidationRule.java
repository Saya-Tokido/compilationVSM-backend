package com.ljz.compilationVSM.api.valid.rule;

import com.ljz.compilationVSM.api.valid.annotation.NotBlank;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * 字符串非空校验规则
 *
 * @author 劳金赞
 * @since 2025-03-04
 */
public class NotBlankValidationRule implements ValidationRule {
    @Override
    public boolean isValid(Object value, Annotation annotation) {
        if (Objects.isNull(value)) {
            return false;
        }
        if (value instanceof String str) {
            return StringUtils.isNotBlank(str);
        }
        return false;
    }

    @Override
    public String getErrorMessage(Annotation annotation) {
        NotBlank notNull = (NotBlank) annotation;
        return notNull.message();
    }
}