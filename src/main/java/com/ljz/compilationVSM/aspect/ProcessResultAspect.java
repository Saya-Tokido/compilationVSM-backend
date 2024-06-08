package com.ljz.compilationVSM.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 全局异常处理业务层切面
 *
 */
@Order(-2)
@Aspect
@Component
@Slf4j
public class ProcessResultAspect {

    /**
     * 切入点
     */
    @Pointcut("execution(public * com.ljz.compilationVSM.domain.task.*.*.*(..))")
    private void commonPointcut() {
    }

    @AfterThrowing(value = "commonPointcut()",throwing = "ex")
    public void logAfterThrowing(Exception ex) {
        log.error("Exception caught: ", ex);
    }
}