package com.example.xiaomidemo;

import com.example.xiaomidemo.domain.repository.WarningRuleRepository;
import com.google.errorprone.annotations.CompileTimeConstant;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@MapperScan("com.example.xiaomidemo.infrastructure.persistence.Mapper")
public class XiaomiDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(XiaomiDemoApplication.class, args);
    }

}
