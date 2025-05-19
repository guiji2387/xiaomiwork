package com.example.xiaomidemo.infrastructure.mq;

import lombok.Data;

import java.util.List;

@Data
public class BatterySignal {
    private String batteryId;
    private String batteryType; // 三元电池 / 铁锂电池
    private int ruleId;         // 对应规则编号，如 1=电压差，2=电流差
    private List<Double> voltages;
    private List<Double> currents;
}
