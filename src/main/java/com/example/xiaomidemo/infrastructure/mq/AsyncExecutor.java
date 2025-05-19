package com.example.xiaomidemo.infrastructure.mq;

import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class AsyncExecutor {
    private final ExecutorService executor = Executors.newFixedThreadPool(8);

    public void execute(Runnable task) {
        executor.submit(task);
    }
}