package com.ljz.compilationVSM.common.utils;

import java.util.concurrent.*;

import org.springframework.stereotype.Component;

@Component
public class ThreadPoolUtil {

    private final ExecutorService executorService;

    public ThreadPoolUtil() {
        // 初始化线程池
        this.executorService = new ThreadPoolExecutor(
                4,  // 核心线程数
                10, // 最大线程数
                60L, // 空闲线程存活时间
                TimeUnit.SECONDS, // 时间单位
                new LinkedBlockingQueue<>(100), // 阻塞队列
                Executors.defaultThreadFactory(), // 默认线程工厂
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    // 提交任务到线程池
    public Future<?> submitTask(Runnable task) {
        return executorService.submit(task);
    }

    // 提交带返回值的任务
    public <T> Future<T> submitTask(Callable<T> task) {
        return executorService.submit(task);
    }

    // 执行任务（无返回值）
    public void executeTask(Runnable task) {
        executorService.execute(task);
    }

    // 关闭线程池
    public void shutDown() {
        executorService.shutdown();
    }

    // 获取线程池的状态
    public String getStatus() {
        if (executorService.isShutdown()) {
            return "Shutdown";
        } else if (executorService.isTerminated()) {
            return "Terminated";
        }
        return "Running";
    }
}
