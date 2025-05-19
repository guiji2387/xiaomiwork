package com.example.xiaomidemo.infrastructure.cache;

import com.example.xiaomidemo.domain.model.BatteryType;
import com.example.xiaomidemo.domain.model.WarningRule;
import com.example.xiaomidemo.infrastructure.persistence.model.WarningRuleDO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

// infrastructure/cache/RuleCache.java
@Component
@RequiredArgsConstructor
@CacheConfig(cacheNames = "rules")
public class RuleCache {
    @Autowired
    private final RedisTemplate<String, Object> redisTemplate;
    private static final Duration TTL = Duration.ofMinutes(5);
    private static final String CACHE_KEY_PREFIX = "Rule:";

    public List<WarningRuleDO> getByRuleId(Integer ruleId) {
        String key = buildKey(ruleId);
        Object cached = redisTemplate.opsForValue().get(key);
        if (cached instanceof List<?>) {
            return (List<WarningRuleDO>) cached;
        }
        return null;
    }

    public void cacheRule(Integer ruleId, List<WarningRuleDO> rules) {
        String key = buildKey(ruleId);
        redisTemplate.opsForValue().set(key, rules, TTL);
    }

    private String buildKey(Integer ruleId) {
        return CACHE_KEY_PREFIX + ruleId;
    }
}