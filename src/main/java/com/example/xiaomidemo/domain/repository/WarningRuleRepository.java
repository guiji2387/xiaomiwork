package com.example.xiaomidemo.domain.repository;

import com.example.xiaomidemo.domain.model.BatteryType;
import com.example.xiaomidemo.domain.model.RuleSegment;
import com.example.xiaomidemo.domain.model.VehicleInfo;
import com.example.xiaomidemo.domain.model.WarningRule;
import com.example.xiaomidemo.infrastructure.persistence.model.WarningRuleDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface WarningRuleRepository {
    boolean add(WarningRuleDO ruleDO);

    List<WarningRuleDO> findByRuleId(Integer ruleId);

    List<WarningRuleDO> findRulesByBatteryType(String batteryType);

    WarningRuleDO convertToDO(WarningRule domain);

//    void save(WarningRule warningRule);

    //    @Override
    //    public void save(WarningRule rule) {
    //        // 更新数据库
    //        WarningRuleDO ruleDO = convertToDO(rule);
    //        warningRuleMapper.update(ruleDO);
    //        // 更新缓存
    //        ruleCache.cacheRule(rule);
    //    }
    WarningRule convertToDomain(WarningRuleDO ruleDO);

    List<RuleSegment> parseSegments(String json);
}
