package com.example.xiaomidemo.infrastructure.util;

import com.example.xiaomidemo.domain.exception.SignalParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
public class SignalParser {
    private static final ObjectMapper mapper = new ObjectMapper();

    public SignalData parse(String json) {
        try {
            JsonNode root = mapper.readTree(json);
            SignalData data = new SignalData();

            // 动态提取信号值
            if (root.has("Mx")) data.setMx(root.get("Mx").asDouble());
            if (root.has("Mi")) data.setMi(root.get("Mi").asDouble());
            if (root.has("Ix")) data.setIx(root.get("Ix").asDouble());
            if (root.has("Ii")) data.setIi(root.get("Ii").asDouble());

            return data;
        } catch (JsonProcessingException e) {
            throw new SignalParseException("信号数据解析失败: " + json, json);
        }
    }

    // 信号数据载体
    @Data
    public static class SignalData {
        private Double mx; // 最高电压
        private Double mi; // 最低电压
        private Double ix; // 最高电流
        private Double ii; // 最低电流
    }
}