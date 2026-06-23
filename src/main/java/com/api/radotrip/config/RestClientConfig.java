package com.api.radotrip.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient() {
        // HTTP 요청 타임아웃 설정 (연결 및 읽기 제한시간 설정)
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout((int) Duration.ofSeconds(10).toMillis());
        requestFactory.setReadTimeout((int) Duration.ofSeconds(10).toMillis());

        return RestClient.builder()
                .requestFactory(requestFactory)
                // 필요 시 글로벌 헤더 설정 가능:
                // .defaultHeader("Accept", "application/json")
                .build();
    }
}
