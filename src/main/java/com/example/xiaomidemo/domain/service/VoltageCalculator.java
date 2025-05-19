package com.example.xiaomidemo.domain.service;

import com.example.xiaomidemo.infrastructure.util.SignalParser;
import org.springframework.stereotype.Component;

@Component
public class VoltageCalculator {
    public static double calculateVoltageDiff(SignalParser.SignalData data) {
        return data.getMx() - data.getMi();
    }
}
