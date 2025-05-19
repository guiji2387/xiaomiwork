package com.example.xiaomidemo.domain.exception;

public class VehicleNotFoundException extends RuntimeException {
    private final String carId;

    public VehicleNotFoundException(String carId) {
        super("车辆不存在: " + carId);
        this.carId = carId;
    }

    public String getCarId() {
        return carId;
    }
}
