package com.example.xiaomidemo.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SignalBatch {
    private List<SignalRecord> records;
    public SignalBatch() {
    }
    @Data
    @AllArgsConstructor
    public static class SignalRecord {
        public SignalRecord() {
            // Jackson 需要无参构造函数
        }
        private String carId;       // 车辆ID（对应车架编号）
        private String warnId;   // 警告ID
        private String signalData;   // 信号JSON字符串（如 {"Mx":3.2,"Mi":2.8}）
        private LocalDateTime timestamp;
    }
}