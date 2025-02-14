package com.ljz.compilationVSM.web.schedule.job;

import com.ljz.compilationVSM.domain.oj.service.OJService;
import com.ljz.compilationVSM.web.schedule.JobHandle;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 查重代码映射构建定时任务
 *
 * @author ljz
 * @since 2025-02-06
 */
@AllArgsConstructor
@JobHandle(value = "plagiarismJob", cronExpression = "0 0 */2 * * ?")
@Slf4j
public class PlagiarismDetectionPreJob implements Job {

    private final OJService ojService;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("代码查重预备定时任务开始执行...");
        long startTime = System.currentTimeMillis();
        ojService.plagiarismDetectionPre();
        log.info("代码查重预备定时任务执行完成!耗时 {}ms",System.currentTimeMillis()-startTime);
    }
}
