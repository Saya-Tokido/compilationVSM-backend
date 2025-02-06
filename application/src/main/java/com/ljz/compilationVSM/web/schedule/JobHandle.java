package com.ljz.compilationVSM.web.schedule;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JobHandle annotation<br/>
 * recorded the job name and cycle with cron expression
 *
 * @author ljz
 * @since 2025-02-06
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface JobHandle {
    @AliasFor(annotation = Component.class)
    String value();

    String cronExpression();
}
