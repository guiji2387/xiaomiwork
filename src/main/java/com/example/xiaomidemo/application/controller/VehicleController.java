package com.example.xiaomidemo.application.controller;


import com.example.xiaomidemo.application.dto.Response;
import com.example.xiaomidemo.domain.model.VehicleInfo;
import com.example.xiaomidemo.domain.repository.VehicleRepository;
import com.example.xiaomidemo.infrastructure.persistence.model.VehicleInfoDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    @Autowired
    private VehicleRepository vehicleRepository;

    @PostMapping
    public Response<?> createVehicle(@RequestBody VehicleInfoDO vehicleInfoDO) {
        try {
            if (vehicleRepository.add(vehicleInfoDO)) return Response.success();
        } catch (Exception e) {
            return Response.error("Failed to add vehicle: " + e.getMessage());
        }
        return Response.success();
    }

    @GetMapping
    public Response<List<VehicleInfoDO>> listVehicles(
            @RequestParam(required = false) String carId
    ) {

        Optional<VehicleInfo> optionalVehicleInfo = vehicleRepository.findbyCarId(carId);
        Optional<VehicleInfoDO> optionalVehicleInfoDO = optionalVehicleInfo.map(vehicleRepository::convertToDO);
        List<VehicleInfoDO> vehicleInfoDOList = optionalVehicleInfoDO
                .map(Collections::singletonList) // 将值变为一个只有一个元素的List
                .orElse(Collections.emptyList()); // 如果为空，返回空列表
        return Response.success(vehicleInfoDOList);
    }
}
