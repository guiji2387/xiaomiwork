package com.example.xiaomidemo.infrastructure.persistence;

import com.example.xiaomidemo.domain.model.BatteryType;
import com.example.xiaomidemo.domain.model.VehicleInfo;
import com.example.xiaomidemo.domain.repository.VehicleRepository;
import com.example.xiaomidemo.infrastructure.cache.VehicleCache;
import com.example.xiaomidemo.infrastructure.persistence.Mapper.VehicleMapper;
import com.example.xiaomidemo.infrastructure.persistence.model.VehicleInfoDO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VehicleRepositoryImpl implements VehicleRepository {
    @Autowired
    private final VehicleMapper vehicleMapper;
    @Autowired
    private final VehicleCache vehicleCache;

    @Override
    public Optional<VehicleInfo> findbyCarId(String carId) {
        // 先查缓存
        Optional<VehicleInfo> cached = vehicleCache.getByVinCode(carId);
        if (cached.isPresent()) return cached;
        System.out.println(carId);
        // 查数据库
        VehicleInfoDO infoDO = vehicleMapper.selectbyCarId(carId);
        if (infoDO == null) {
            return Optional.empty();

        }
        // 转换并缓存
        VehicleInfo domain = convertToDomain(infoDO);
        vehicleCache.cacheVehicle(domain);
        return Optional.of(domain);
    }

    @Override
    public void save(VehicleInfo vehicle) {
        // 更新数据库
        VehicleInfoDO infoDO = convertToDO(vehicle);
        vehicleMapper.update(infoDO);

        // 更新缓存
        vehicleCache.cacheVehicle(vehicle);
    }

    @Override
    public boolean add(VehicleInfoDO vehicleInfoDO) {
        // 插入数据库
        VehicleInfo info = convertToDomain(vehicleInfoDO);
        vehicleMapper.insert(vehicleInfoDO);
        // 更新缓存
        vehicleCache.cacheVehicle(info);
        return true;
    }
    @Override
    public VehicleInfo convertToDomain(VehicleInfoDO infoDO) {
        return VehicleInfo.builder()
                .id(infoDO.getId())
                .vid(infoDO.getVid())
                .carId(infoDO.getCarId())
                .batteryType(BatteryType.fromDisplayName(infoDO.getBatteryType()))
                .mileage(infoDO.getMileage())
                .batteryHealth(infoDO.getBatteryHealth())
                .build();
    }
    @Override
    public VehicleInfoDO convertToDO(VehicleInfo domain) {
        return new VehicleInfoDO(
                domain.getId(),
                domain.getVid(),
                domain.getCarId(),
                domain.getBatteryType().getDisplayName(),
                domain.getMileage(),
                domain.getBatteryHealth()
        );
    }

}