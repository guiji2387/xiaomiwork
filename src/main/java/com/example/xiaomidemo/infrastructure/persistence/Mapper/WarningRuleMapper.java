package com.example.xiaomidemo.infrastructure.persistence.Mapper;

import com.example.xiaomidemo.domain.model.BatteryType;
import com.example.xiaomidemo.domain.model.WarningRule;
import com.example.xiaomidemo.infrastructure.persistence.model.VehicleInfoDO;
import com.example.xiaomidemo.infrastructure.persistence.model.WarningRuleDO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface WarningRuleMapper {
    @Select("SELECT * FROM warning_rule WHERE rule_id = #{ruleId}")
    List<WarningRuleDO> selectByRuleId(Integer ruleId);

    @Update("UPDATE warning_rule SET rule_id = #{ruleId}, battery_type = #{batteryType}, signal_type = #{signalType}, " +
            "min_value = #{minValue}, max_value = #{maxValue}, level = #{level} WHERE rule_id = #{ruleId}")
    void update(WarningRuleDO warningRuleDO);

    @Select("SELECT * FROM warning_rule WHERE battery_type = #{name}")
    List<WarningRuleDO> selectByBatteryType(String name);

    @Insert("INSERT INTO warning_rule (rule_id, battery_type, signal_type,segments) " +
            "VALUES (#{ruleId}, #{batteryType}, #{signalType},#{segments})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(WarningRuleDO warningRuleDO);
}