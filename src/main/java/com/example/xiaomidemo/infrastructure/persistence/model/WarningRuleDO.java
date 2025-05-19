package com.example.xiaomidemo.infrastructure.persistence.model;

import com.example.xiaomidemo.domain.model.BatteryType;
import com.example.xiaomidemo.domain.model.RuleSegment;
import com.example.xiaomidemo.domain.model.WarningRule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarningRuleDO {
    private Long id;
    private Integer ruleId;
    private String batteryType;
    private String signalType; // 电压差/电流差
    private String segments;

}
