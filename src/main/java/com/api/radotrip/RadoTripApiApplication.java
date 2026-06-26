package com.api.radotrip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication // Spring Boot 자동 설정
@EnableScheduling // 스케줄링 활성화
public class RadoTripApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RadoTripApiApplication.class, args);
    }
}
