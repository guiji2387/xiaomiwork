package com.example.xiaomidemo.domain.service;

import com.example.xiaomidemo.domain.model.WarningRule;
import com.example.xiaomidemo.infrastructure.util.SignalParser;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
@RequiredArgsConstructor
@Controller
public class DifferenceCalculator {
    public static double calculateDifference(WarningRule rule, SignalParser.SignalData data) {
        switch (rule.getSignalType()) {
            case "电压差报警":
                return VoltageCalculator.calculateVoltageDiff(data);
            case "电流差报警":
                return CurrentCalculator.calculateCurrentDiff(data);
            default:
                throw new UnsupportedOperationException("不支持的信号类型: " + rule.getSignalType());
        }
    }
}

