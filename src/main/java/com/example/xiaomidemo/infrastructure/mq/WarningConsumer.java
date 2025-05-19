package com.example.xiaomidemo.infrastructure.mq;


import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RocketMQMessageListener(topic = "battery-warning-topic", consumerGroup = "battery-warning-consumer")
public class WarningConsumer implements RocketMQListener<BatterySignal> {

    @Autowired
    private mqproducer ruleCache;

    @Autowired
    private AsyncExecutor executor;

    @Override
    public void onMessage(BatterySignal signal) {
        executor.execute(() -> processSignal(signal));
    }

    private void processSignal(BatterySignal signal) {
        List<WarningRule> rules = ruleCache.getRules(signal.getRuleId(), signal.getBatteryType());
        if (rules == null || rules.isEmpty()) return;

        double diff = 0;
        if (signal.getRuleId() == 1) { // 电压差
            diff = Collections.max(signal.getVoltages()) - Collections.min(signal.getVoltages());
        } else if (signal.getRuleId() == 2) { // 电流差
            diff = Collections.max(signal.getCurrents()) - Collections.min(signal.getCurrents());
        }

        for (WarningRule rule : rules) {
            int level = WarningLevelResolver.resolve(rule.getRuleExpression(), diff);
            if (level >= 0) {
                System.out.printf("⚠️ 预警！BatteryId: %s，类型: %s，规则: %s，差值: %.2f，等级: %d%n",
                        signal.getBatteryId(), signal.getBatteryType(), rule.getSignalType(), diff, level);
            }
        }
    }
}

