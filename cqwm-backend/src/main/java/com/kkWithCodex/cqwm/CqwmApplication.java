package com.kkWithCodex.cqwm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.kkWithCodex.cqwm.auth.repository")
public class CqwmApplication {

    public static void main(String[] args) {
        SpringApplication.run(CqwmApplication.class, args);
    }

}
