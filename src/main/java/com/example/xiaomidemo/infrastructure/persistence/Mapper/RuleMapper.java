package com.example.xiaomidemo.infrastructure.persistence.Mapper;

import com.example.xiaomidemo.infrastructure.mq.WarningRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RuleMapper {
    @Select("SELECT * FROM warning_rule")
    List<WarningRule> selectAll();
}