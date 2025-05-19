package com.example.xiaomidemo.infrastructure.mq;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@Component
public class mqproducer {
    private final Map<String, List<WarningRule>> cache = new ConcurrentHashMap<>();

    @Autowired
    private WarningRuleService ruleService;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    private final ExecutorService executor = Executors.newFixedThreadPool(4); // 异步线程池

    @PostConstruct
    @Scheduled(fixedRate = 10 * 1000)
    public void sendmessage() {

        System.out.println("开始加载数据到缓存...");
        List<WarningRule> result = ruleService.loadAllRules();
        executor.submit(() -> {
//            rocketMQTemplate.asyncSend(TOPIC, result, new SendCallback() {
//                @Override
//                public void onSuccess(SendResult sendResult) {
//                    System.out.println("消息发送成功: " + sendResult);
//                }
//
//                @Override
//                public void onException(Throwable e) {
//                    System.err.println("消息发送失败: " + e.getMessage());
//                }
//            });
            for (WarningRule rule : result) {
                rocketMQTemplate.asyncSend(TOPIC, rule, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        System.out.println("消息发送成功: " + sendResult);
                    }

                    @Override
                    public void onException(Throwable e) {
                        System.err.println("消息发送失败: " + e.getMessage());
                    }
                });
            }
        });

    }

    public List<WarningRule> getRules(int ruleId, String batteryType) {
        return cache.get(ruleId + "_" + batteryType);
    }
}

