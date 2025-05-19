package com.example.xiaomidemo.infrastructure.cache;

import com.example.xiaomidemo.domain.model.VehicleInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.time.Duration;
import java.util.Optional;

@Component
@RequiredArgsConstructor
//@Controller
public class VehicleCache {
    @Autowired
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String CACHE_KEY_PREFIX = "vehicle:";
    private static final Duration TTL = Duration.ofMinutes(5);

    public Optional<VehicleInfo> getByVinCode(String carId) {
        String key = buildKey(carId);   // 车辆唯一标识
        VehicleInfo cached = (VehicleInfo) redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(cached);
    }

    public void cacheVehicle(VehicleInfo vehicle) {
        String key = buildKey(vehicle.getCarId());   // 车辆唯一标识
        redisTemplate.opsForValue().set(key, vehicle, TTL);   // 设置过期时间

    }

    public void evictCache(String carId) {
        redisTemplate.delete(buildKey(carId));
    }

    private String buildKey(String carId) {
        return CACHE_KEY_PREFIX + carId;
    }
}