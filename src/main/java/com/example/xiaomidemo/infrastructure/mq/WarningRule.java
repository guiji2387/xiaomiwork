package com.example.xiaomidemo.infrastructure.mq;

import lombok.Data;

@Data
public class WarningRule {
    private int ruleId;
    private String batteryType;
    private String signalType;             // 电压差报警等
    private String ruleExpression;   // 多个条件，例如 "5<=(Mx-Mi):0;3<=(Mx-Mi)<5:1;..."
}

