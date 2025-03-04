package com.ljz.compilationVSM.web.config.aspect;

import com.ljz.compilationVSM.api.valid.Valid;
import com.ljz.compilationVSM.api.valid.Validator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Parameter;
import java.util.List;

/**
 * 请求入参校验切面
 *
 * @author 劳金赞
 * @since 2025-03-04
 */
@Aspect
// 优先级低于异常日志处理
@Order(1)
@Component
public class ValidationAspect {


    /**
     * 切入点
     */
    @Pointcut("execution(public * com.ljz.compilationVSM.web.soa..*(..))")
    private void commonPointcut() {
    }

    /**
     * 方法执行前校验入参
     */
    @Before("commonPointcut()")
    public void beforeMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Parameter[] parameters = signature.getMethod().getParameters();
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].isAnnotationPresent(Valid.class)) {
                Object arg = args[i];
                Validator.validate(arg);
            }
        }
    }
}