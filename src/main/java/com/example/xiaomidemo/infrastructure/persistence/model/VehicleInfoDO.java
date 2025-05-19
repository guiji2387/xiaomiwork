package com.example.xiaomidemo.infrastructure.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleInfoDO {
    private Long id;
    private String vid;
    private String carId; // 车架号
    private String batteryType; // 数据库存储字符串
    private Integer mileage;
    private Integer batteryHealth;
}
