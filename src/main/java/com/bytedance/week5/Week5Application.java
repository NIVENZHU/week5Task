package com.bytedance.week5;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.bytedance.week5.model.dao")
public class Week5Application {
    public static void main(String[] args) {
        SpringApplication.run(Week5Application.class, args);
    }

}
