package com.ljz.compilationVSM.api.valid;

import com.ljz.compilationVSM.api.valid.rule.ValidationRule;
import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.common.exception.BizExceptionCodeEnum;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 验证器类
 *
 * @author 劳金赞
 * @since 2025-03-04
 */
@Slf4j
public class Validator {

    /**
     * 参数校验
     *
     * @param object 校验的对象
     */
    public static void validate(Object object) {
        if (Objects.isNull(object)) {
            log.error("请求入参校验，校验异常!");
            throw new BizException(BizExceptionCodeEnum.SERVER_ERROR);
        }
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations) {
                ValidationRule rule = ValidatorRegistry.getRule(annotation.annotationType());
                if (rule != null) {
                    try {
                        Object value = field.get(object);
                        if (!rule.isValid(value, annotation)) {
                            throw new BizException(BizExceptionCodeEnum.PARAMETER_ERROR);
                        }
                    } catch (IllegalAccessException e) {
                        log.error("请求入参校验，无法获取对象属性");
                        throw new BizException(BizExceptionCodeEnum.SERVER_ERROR);
                    }
                }
            }
        }
    }
}