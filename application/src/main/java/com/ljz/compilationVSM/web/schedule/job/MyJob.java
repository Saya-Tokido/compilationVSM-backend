package com.ljz.compilationVSM.web.schedule.job;

import com.ljz.compilationVSM.web.schedule.JobHandle;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * my job
 *
 * @author ljz
 * @since 2025-02-06
 */
@JobHandle(value = "myJob", cronExpression = "*/20 * * * * ?")
public class MyJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("定时任务执行一次" + LocalDateTime.now());
    }
}

