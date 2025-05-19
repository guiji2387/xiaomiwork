package com.example.xiaomidemo.infrastructure.mq;

import com.googlecode.aviator.AviatorEvaluator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WarningEvaluatorService {

    private final mqproducer ruleCCache;

    public void evaluate(BatterySignal signal) {
        Double value = 0.0;
        List<WarningRule> rules = ruleCCache.getRules(signal.getRuleId(), signal.getBatteryType());
        for (WarningRule rule : rules) {
            value = switch (rule.getSignalType()) {
                case "电压差报警" -> Collections.max(signal.getVoltages()) - Collections.min(signal.getVoltages());
                case "电流差报警" -> Collections.max(signal.getCurrents()) - Collections.min(signal.getCurrents());
                default -> 0.0;
            };
        }
        for (WarningRule rule : rules) {
            int level = WarningLevelResolver.resolve(rule.getRuleExpression(), value);
            if (level >= 0) {
                System.out.printf("⚠️ 预警！BatteryId: %s，类型: %s，规则: %s，差值: %.2f，等级: %d%n",
                        signal.getBatteryId(), signal.getBatteryType(), rule.getSignalType(), value, value);
            }
        }
    };

    private boolean evaluateExpr(String expr, double value) {
        String formula = expr.replace("差值", String.valueOf(value));
        return AviatorEvaluator.execute(formula).equals(true);
    }
}
