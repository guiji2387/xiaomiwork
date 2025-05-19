package com.example.xiaomidemo.domain.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

// 文件路径：src/main/java/com/example/xiaomidemo/domain/exception/RuleParseException.java

public class RuleParseException extends RuntimeException {

    private final String rawData;

    public RuleParseException(String message, String rawData) {
        super(message);
        this.rawData = rawData;
    }

    public RuleParseException(String message, String rawData, Throwable cause) {
        super(message, cause);
        this.rawData = rawData;
    }

    // 可选：获取原始数据用于调试
    public String getRawData() {
        return rawData;
    }
}

