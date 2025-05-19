package com.example.xiaomidemo.domain.repository;

import com.example.xiaomidemo.domain.model.VehicleInfo;
import com.example.xiaomidemo.infrastructure.persistence.model.VehicleInfoDO;

import java.util.Optional;

public interface VehicleRepository {
    Optional<VehicleInfo> findbyCarId(String vid);
    void save(VehicleInfo vehicle);

    boolean add(VehicleInfoDO vehicleInfoDO);

    VehicleInfo convertToDomain(VehicleInfoDO infoDO);

    VehicleInfoDO convertToDO(VehicleInfo domain);
}
