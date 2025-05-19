package com.example.xiaomidemo.application.dto;

import com.example.xiaomidemo.domain.model.BatteryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Controller
@NoArgsConstructor
public class WarnResult {
//    private Long id;
    private String carId;
    private String batteryType;
    private String warnName;
    private Integer warnLevel;
    public String toMQMessage() {
        return String.format(
                "{车架编号:%d, 电池类型:'%s', 预警名称:'%s', 预警等级:%d}",
                carId, batteryType, warnName, warnLevel
        );
    }
}
