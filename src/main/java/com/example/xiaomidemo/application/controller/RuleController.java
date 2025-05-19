package com.example.xiaomidemo.application.controller;

import com.example.xiaomidemo.application.dto.Response;
import com.example.xiaomidemo.application.dto.WarnResult;
import com.example.xiaomidemo.domain.model.VehicleInfo;
import com.example.xiaomidemo.domain.model.WarningRule;
import com.example.xiaomidemo.domain.repository.VehicleRepository;
import com.example.xiaomidemo.domain.repository.WarningRuleRepository;
import com.example.xiaomidemo.infrastructure.persistence.model.VehicleInfoDO;
import com.example.xiaomidemo.infrastructure.persistence.model.WarningRuleDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rules")
public class RuleController {
    @Autowired
    private WarningRuleRepository warningRuleRepository;

    @PostMapping
    public Response<?> addRules(@RequestBody WarningRuleDO dtos) {
        try {
            System.out.println("Received request to add rules: " + dtos);
            if (warningRuleRepository.add(dtos)) return Response.success();
        } catch (Exception e) {
            return Response.error("Failed to add vehicle: " + e.getMessage());
        }
        return Response.success();
    }

    @GetMapping
    public Response<List<WarningRuleDO>> listRules(
            @RequestParam(required = false) Integer ruleId
    ) {
        List<WarningRuleDO> ruleList = warningRuleRepository.findByRuleId(ruleId);
        return Response.success(ruleList);
    }
}
