package com.example.xiaomidemo.infrastructure.persistence;

import com.example.xiaomidemo.domain.exception.RuleConversionException;
import com.example.xiaomidemo.domain.exception.RuleParseException;
import com.example.xiaomidemo.domain.model.BatteryType;
import com.example.xiaomidemo.domain.model.RuleSegment;
import com.example.xiaomidemo.domain.model.WarningRule;
import com.example.xiaomidemo.domain.repository.WarningRuleRepository;
import com.example.xiaomidemo.infrastructure.cache.RuleCache;
import com.example.xiaomidemo.infrastructure.persistence.Mapper.WarningRuleMapper;
import com.example.xiaomidemo.infrastructure.persistence.model.WarningRuleDO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
// 确保导入正确的类
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class WaringRuleRepositoryImpl implements WarningRuleRepository {
    @Autowired
    private final WarningRuleMapper warningRuleMapper;
    @Autowired
    private final RuleCache ruleCache;
    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<WarningRuleDO> findRulesByBatteryType(String batteryType) {
        List<WarningRuleDO> ruleDOs = warningRuleMapper.selectByBatteryType(batteryType);
        return ruleDOs;
    }

    @Override
    public boolean add(WarningRuleDO ruleDO) {

        // 插入数据库
        warningRuleMapper.insert(ruleDO);
        // 更新缓存
        WarningRule rule = convertToDomain(ruleDO);
        return true;
    }

    @Override
    public List<WarningRuleDO> findByRuleId(Integer ruleId) {
        System.out.println(ruleId);
        // 先查缓存
        List<WarningRuleDO> cached = ruleCache.getByRuleId(ruleId);
        if (cached != null && !cached.isEmpty()) {
            return cached;
        }

        // 查数据库
        List<WarningRuleDO> warningRuleDOList = warningRuleMapper.selectByRuleId(ruleId);
        // 转换并缓存
        ruleCache.cacheRule(ruleId, warningRuleDOList);
        return warningRuleDOList;
    }

    @Override
    public WarningRuleDO convertToDO(WarningRule domain) {
        return new WarningRuleDO(
                domain.getId(),
                domain.getRuleId(),
                domain.getBatteryType().getDisplayName(), // 枚举转字符串
                domain.getSignalType(),
                serializeSegments(domain.getSegments()) // 列表转JSON
        );
    }

    private String serializeSegments(List<RuleSegment> segments) {
        try {
            return objectMapper.writeValueAsString(segments);
        } catch (JsonProcessingException e) {
            throw new RuleConversionException("规则分段序列化失败", e);
        }
    }

    //    @Override
//    public void save(WarningRule rule) {
//        // 更新数据库
//        WarningRuleDO ruleDO = convertToDO(rule);
//        warningRuleMapper.update(ruleDO);
//        // 更新缓存
//        ruleCache.cacheRule(rule);
//    }
    @Override
    public WarningRule convertToDomain(WarningRuleDO ruleDO) {
        return WarningRule.builder()
                .id(ruleDO.getId())
                .ruleId(ruleDO.getRuleId())
                .batteryType(BatteryType.fromDisplayName(ruleDO.getBatteryType()))
                .signalType(ruleDO.getSignalType())
                .segments(parseSegments(ruleDO.getSegments())) // JSON字符串转List
                .build();
    }

    private BatteryType convertBatteryType(String batteryTypeStr) {
        try {
            return BatteryType.valueOf(batteryTypeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuleConversionException("无效电池类型: " + batteryTypeStr);
        }
    }

    public List<RuleSegment> parseSegments(String json) {
        if (json == null || json.isBlank()) {
            // 如果 json 是空的，说明没有任何规则分段，直接返回空列表
            return Collections.emptyList();
        }
        try {
            return objectMapper.readValue(json, new TypeReference<List<RuleSegment>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuleParseException("规则分段解析失败", json);
        }
    }
}
