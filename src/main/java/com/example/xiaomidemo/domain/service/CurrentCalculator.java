package com.example.xiaomidemo.domain.service;

import com.example.xiaomidemo.infrastructure.util.SignalParser;
import org.springframework.stereotype.Component;

// domain/service/CurrentCalculator.java
@Component
public class CurrentCalculator {
    public static double calculateCurrentDiff(SignalParser.SignalData data) {
        return data.getIx() - data.getIi();
    }
}
