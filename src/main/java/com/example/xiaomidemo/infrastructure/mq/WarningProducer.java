package com.example.xiaomidemo.infrastructure.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class WarningProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    // 建议用配置文件读取 topic
    private static final String TOPIC = "warn-topic";

    // 异步线程池
    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    /**
     * 批量处理并异步发送预警消息
     */
    public void processSignals(List<BatterySignal> signalList, mqproducer ruleCache) {
        List<WarningMessage> messageList = new ArrayList<>();

        for (BatterySignal signal : signalList) {
            List<WarningRule> rules = ruleCache.getRules(signal.getRuleId(), signal.getBatteryType());
            if (rules == null || rules.isEmpty()) continue;

            double diff = computeDiff(signal);

            for (WarningRule rule : rules) {
                int level = WarningLevelResolver.resolve(rule.getRuleExpression(), diff);
                if (level >= 0) {
                    WarningMessage msg = new WarningMessage(
                            signal.getBatteryId(),
                            signal.getBatteryType(),
                            rule.getSignalType(),
                            diff,
                            level
                    );
                    messageList.add(msg);
                }
            }
        }

        if (!messageList.isEmpty()) {
            executor.submit(() -> {
                for (WarningMessage msg : messageList) {
                    try {
                        rocketMQTemplate.convertAndSend(TOPIC, msg);
                        log.info("✅ 成功发送预警消息: {}", msg);
                    } catch (Exception e) {
                        log.error("❌ 发送预警消息失败: {}，原因: {}", msg, e.getMessage(), e);
                    }
                }
            });
        }
    }

    private double computeDiff(BatterySignal signal) {
        if (signal.getRuleId() == 1) { // 电压差
            return Collections.max(signal.getVoltages()) - Collections.min(signal.getVoltages());
        } else if (signal.getRuleId() == 2) { // 电流差
            return Collections.max(signal.getCurrents()) - Collections.min(signal.getCurrents());
        }
        return 0;
    }
}
