package com.example.xiaomidemo.infrastructure.persistence.Mapper;

import com.example.xiaomidemo.infrastructure.persistence.model.VehicleInfoDO;

import org.apache.ibatis.annotations.*;

// infrastructure/persistence/mapper/VehicleMapper.java
// Mapper interface for VehicleInfoDO
@Mapper
public interface VehicleMapper {
    @Select("SELECT * FROM vehicle_info WHERE car_id = #{carId}")
    VehicleInfoDO selectbyCarId(String carId);

    @Insert("INSERT INTO vehicle_info(vid, car_id, battery_type, mileage, battery_health) " +
            "VALUES(#{vid}, #{vinCode}, #{batteryType}, #{mileage}, #{batteryHealth})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(VehicleInfoDO vehicle);
    @Update("UPDATE vehicle_info SET vid = #{vid}, vin_code = #{vinCode}, battery_type = #{batteryType}, " +
            "mileage = #{mileage}, battery_health = #{batteryHealth} WHERE id = #{id}")
    void update(VehicleInfoDO vehicle);
}
