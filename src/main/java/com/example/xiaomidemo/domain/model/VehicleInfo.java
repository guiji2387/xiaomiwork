package com.example.xiaomidemo.domain.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Tolerate;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Data

@Builder
public class VehicleInfo {
    private Long id;
    private String vid;
    private String carId;
    private BatteryType batteryType;
    private Integer mileage;
    private Integer batteryHealth;
    @Tolerate
    public VehicleInfo() {
    }
}

