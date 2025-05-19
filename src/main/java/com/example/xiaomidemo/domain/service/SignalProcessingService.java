package com.example.xiaomidemo.domain.service;

import com.example.xiaomidemo.application.dto.SignalBatch;
import com.example.xiaomidemo.application.dto.WarnResult;
import com.example.xiaomidemo.domain.exception.VehicleNotFoundException;
import com.example.xiaomidemo.domain.model.BatterySignal;
import com.example.xiaomidemo.domain.model.VehicleInfo;
import com.example.xiaomidemo.domain.model.WarningRule;
import com.example.xiaomidemo.domain.repository.VehicleRepository;
import com.example.xiaomidemo.domain.repository.WarningRuleRepository;
import com.example.xiaomidemo.infrastructure.cache.RuleCache;
import com.example.xiaomidemo.infrastructure.persistence.model.WarningRuleDO;
import com.example.xiaomidemo.infrastructure.util.SignalParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.*;


// domain/service/SignalProcessingService.java
@Service
@RequiredArgsConstructor
public class SignalProcessingService {
    private final WarningRuleRepository ruleRepo;
    private final VehicleRepository vehicleRepo;
    private VehicleRepository vehicleRepository;

    /**
     * 批量处理信号数据
     *
     * @param batch 信号批次
     * @return 预警结果列表
     */
    public List<WarnResult> processBatch(SignalBatch batch) {
        return batch.getRecords().parallelStream()  // 使用并行流提升处理效率
                .map(this::processSingleRecord)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<WarnResult> processSingleRecord(SignalBatch.SignalRecord record) {
        // 步骤1：获取车辆信息
        VehicleInfo vehicle = vehicleRepo.findbyCarId(record.getCarId())
                .orElseThrow(() -> new VehicleNotFoundException(record.getCarId()));
        SignalParser signalParser = new SignalParser();


        // 步骤2：解析信号数据

        SignalParser.SignalData signal = signalParser.parse(record.getSignalData());
        System.out.println(signal+ " "+vehicle.getBatteryType());
        // 步骤3：获取适用规则（根据电池类型自动匹配）
        List<WarningRuleDO> rules = ruleRepo.findRulesByBatteryType(vehicle.getBatteryType().getDisplayName());

        System.out.println("rules: " + rules);
//        VehicleInfo vehicle = vehicleRepository.findByCarId(record.getCarId());
        // 步骤4：评估所有规则
        return rules.stream()
                .map(rule -> evaluateRule(ruleRepo.convertToDomain(rule), signal, vehicle))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .peek(result -> enrichResult(result, vehicle))
//                .peek(result -> notifier.notifyWarning(result, vehicle))
                .collect(Collectors.toList());
    }

    // 步骤5：评估单个规则
    private Optional<WarnResult> evaluateRule(WarningRule rule, SignalParser.SignalData signal, VehicleInfo vehicle) {
        try {

            double difference = DifferenceCalculator.calculateDifference(rule, signal);
            return rule.match(difference)
                    .map(level -> new WarnResult(
                            vehicle.getCarId(),       // 车架编号
                            vehicle.getBatteryType().getDisplayName(), // 电池类型
                            rule.getSignalType(),          // 预警名称
                            level// 预警等级
                    ));
        } catch (Exception e) {
            System.out.println("规则评估失败:" + rule.getRuleId());
            return Optional.empty();
        }
    }

    private void enrichResult(WarnResult result, VehicleInfo vehicle) {
        result.setCarId(vehicle.getCarId());
        result.setBatteryType(vehicle.getBatteryType().getDisplayName());
    }
}
