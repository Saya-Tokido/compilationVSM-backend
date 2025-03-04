package com.ljz.compilationVSM.api.valid;

import com.ljz.compilationVSM.api.valid.annotation.NotBlank;
import com.ljz.compilationVSM.api.valid.annotation.NotEmpty;
import com.ljz.compilationVSM.api.valid.annotation.NotNull;
import com.ljz.compilationVSM.api.valid.rule.NotBlankValidationRule;
import com.ljz.compilationVSM.api.valid.rule.NotEmptyValidationRule;
import com.ljz.compilationVSM.api.valid.rule.NotNullValidationRule;
import com.ljz.compilationVSM.api.valid.rule.ValidationRule;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * 校验器注册表
 *
 * @author 劳金赞
 * @since 2025-03-04
 */
@Component
class ValidatorRegistry {
    /**
     * 校验规则映射
     */
    private static final Map<Class<? extends Annotation>, ValidationRule> ruleMap = new HashMap<>();

    /**
     * 初始化规则
     */
    @PostConstruct
    public void init() {
        register(NotNull.class, new NotNullValidationRule());
        register(NotBlank.class, new NotBlankValidationRule());
        register(NotEmpty.class, new NotEmptyValidationRule());
    }

    /**
     * 注册规则
     *
     * @param annotationClass 校验注解类
     * @param rule            校验规则类
     */
    public static void register(Class<? extends Annotation> annotationClass, ValidationRule rule) {
        ruleMap.put(annotationClass, rule);
    }

    /**
     * 获取校验规则
     *
     * @param annotationClass 校验注解
     * @return 校验规则
     */
    public static ValidationRule getRule(Class<? extends Annotation> annotationClass) {
        return ruleMap.get(annotationClass);
    }
}