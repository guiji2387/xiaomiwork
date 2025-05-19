package com.example.xiaomidemo.domain.model;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum BatteryType {
    LITHIUM_ION("三元电池"),
        LFP("铁锂电池");

    private final String displayName;

    public static BatteryType fromDisplayName(String displayName) {
        return Arrays.stream(values())
                .filter(e -> e.displayName.equals(displayName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("无效电池类型: " + displayName));
    }

    BatteryType(String displayName) {
        this.displayName = displayName;
    }

}