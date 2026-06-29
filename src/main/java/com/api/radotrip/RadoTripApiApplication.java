package com.api.radotrip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication // Spring Boot 자동 설정
@EnableScheduling // 스케줄링 활성화
@MapperScan("com.api.radotrip.mapper.*") // Mapper 패키지 지정
public class RadoTripApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RadoTripApiApplication.class, args);
    }
}
