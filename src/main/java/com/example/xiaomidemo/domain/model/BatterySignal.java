package com.example.xiaomidemo.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatterySignal {
    private String batteryId;
    private double voltage;
    private double temperature;
}