package com.example.xiaomidemo.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {
    private Integer code;
    private String msg;
    private T data;

    public static <T> Response<T> success(T data) {
        return new Response<>(200, "OK", data);
    }
    public static Response<?> success() {
        return success(null);
    }
    public static Response<?> error(String msg) {
        return new Response<>(500, msg, null);
    }
}
