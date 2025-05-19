package com.example.xiaomidemo.infrastructure.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarningMessage {
    private String batteryId;
    private String batteryType;
    private String signalType;
    private double diff;
    private int level;
}
