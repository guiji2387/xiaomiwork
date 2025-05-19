package com.example.xiaomidemo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfig {

    @Bean(name = "AsyncExecutor")
    public Executor AsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // 核心线程数
        executor.setMaxPoolSize(50);  // 最大线程数
        executor.setQueueCapacity(1000); // 等待队列容量
        executor.setThreadNamePrefix("RuleExecutor-");
        executor.initialize();
        return executor;
    }
}