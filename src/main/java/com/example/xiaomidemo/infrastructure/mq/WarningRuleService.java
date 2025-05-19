package com.example.xiaomidemo.infrastructure.mq;

import com.example.xiaomidemo.infrastructure.persistence.Mapper.RuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarningRuleService {
    @Autowired
    private RuleMapper ruleMapper;

    public List<WarningRule> loadAllRules() {
        return ruleMapper.selectAll();
    }
}
