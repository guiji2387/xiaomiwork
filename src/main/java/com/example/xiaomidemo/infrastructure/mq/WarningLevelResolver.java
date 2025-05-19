package com.example.xiaomidemo.infrastructure.mq;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class WarningLevelResolver {

    public static int resolve(String jsonExpression, double diff) {
        if (jsonExpression == null || jsonExpression.isEmpty()) {
            return -1;
        }
        JSONArray rules = JSON.parseArray(jsonExpression);
        for (int i = 0; i < rules.size(); i++) {
            JSONObject rule = rules.getJSONObject(i);
            double min = rule.getDoubleValue("min");
            Double max = rule.containsKey("max") ? rule.getDoubleValue("max") : null;
            int level = rule.getIntValue("level");

            if (max == null) {
                if (diff >= min) {
                    return level;
                }
            } else {
                if (diff >= min && diff < max) {
                    return level;
                }
            }
        }
        return -1; // 没有匹配任何规则
    }
}
