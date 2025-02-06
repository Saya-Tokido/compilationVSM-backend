package com.ljz.compilationVSM.web.schedule;

import jakarta.annotation.PostConstruct;
import org.quartz.*;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 定时任务配置类
 *
 * @author ljz
 * @since 2025-02-06
 */
@Configuration
@ComponentScan(basePackages = "com.ljz.compilationVSM.web.schedule.job")  // 扫描包含Quartz作业类的包
public class QuartzConfig {

    private final Scheduler scheduler;

    public QuartzConfig(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostConstruct
    public void init() throws Exception {
        // 扫描所有带有@JobHandle注解的类
        Set<Class<?>> jobClasses = findJobClassesWithAnnotation();
        
        for (Class<?> jobClass : jobClasses) {
            JobHandle jobHandleAnnotation = jobClass.getAnnotation(JobHandle.class);
            String cronExpression = jobHandleAnnotation.cronExpression();
            String jobName = jobHandleAnnotation.value();

            // 创建JobDetail
            JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) jobClass)
                    .withIdentity(jobName)
                    .build();

            // 创建Trigger
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(jobName + "Trigger")
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                    .build();

            // 将作业和触发器添加到调度器中
            scheduler.scheduleJob(jobDetail, trigger);
        }
    }

    // 查找带有@JobHandle注解的类
    private Set<Class<?>> findJobClassesWithAnnotation() {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(JobHandle.class));
        Set<BeanDefinition> jobs = provider.findCandidateComponents("com.ljz.compilationVSM.web.schedule.job");

        return jobs.stream()
                .map(job -> {
                    try {
                        return Class.forName(job.getBeanClassName());
                    } catch (ClassNotFoundException e) {
                        return null;
                    }
                })
                .collect(Collectors.toSet());
    }
}
