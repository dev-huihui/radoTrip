package com.api.radotrip.client;

// Removed TourApiResponse import as we will return raw JSON
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Component
@RequiredArgsConstructor
public class TourApiClient {

    private final RestClient restClient;

    @Value("${tourApi.serviceKey}")
    private String serviceKey;

    @Value("${tourApi.baseUrl}")
    private String baseUrl;

    // 2026.06.24 전주국제 영화제 정보 가져오기
    public String fetchJeonjuFestivalInfo() {
        URI uri = UriComponentsBuilder.fromUriString(baseUrl + "/B553457/cultureinfo/area2")
                .queryParam("serviceKey", serviceKey)
                .queryParam("numOfRows", 100)
                .queryParam("pageNo", 1)
                .queryParam("sido", "전북특별자치도")
                .queryParam("from", "20260101")
                .queryParam("to", "20261231")
                .queryParam("keyword", "영화제")
                .build(true)
                .toUri();

        log.info("Requesting TourAPI: {}", uri);

        try {
            return restClient.get()
                    .uri(uri)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(String.class);
        } catch (Exception e) {
            log.error("Failed to fetch data from TourAPI", e);
            throw new RuntimeException("Jeonju festival 연동 실패: " + e.getMessage(), e);
        }
    }

    // 2026.06.26 전국 지역정보 가져오기
    public String fetchAreaInfo() {
        // 공공데이터포털 API Key는 디코딩된 키인 경우가 많아 URI 인코딩 처리가 깨지지 않도록 URI 객체 직접 빌드
        URI uri = UriComponentsBuilder.fromUriString(baseUrl + "/B551011/KorService2/ldongCode2")
                .queryParam("serviceKey", serviceKey)
                .queryParam("numOfRows", 9999)
                .queryParam("pageNo", 1)
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "RadoTrip")
                .queryParam("lDongListYn", "Y")
                .queryParam("_type", "json")
                .build(true) // true: 인코딩된 상태 유지
                .toUri();

        log.info("Requesting TourAPI: {}", uri);

        try {
            // RestClient 로부터 JSON 문자열을 그대로 받아 반환합니다.
            return restClient.get()
                    .uri(uri)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(String.class);
        } catch (Exception e) {
            log.error("Failed to fetch data from TourAPI", e);
            throw new RuntimeException("TourAPI(areaInfo) 연동 실패: " + e.getMessage(), e);
        }
    }

    /**
     * 지역 기반 관광정보 조회 (areaBasedList1) – JSON 문자열을 반환합니다.
     */
    public String fetchAreaBasedList(String areaCode, String sigunguCode, String contentTypeId) {
        // 공공데이터포털 API Key는 디코딩된 키인 경우가 많아 URI 인코딩 처리가 깨지지 않도록 URI 객체 직접 빌드
        URI uri = UriComponentsBuilder.fromUriString(baseUrl + "/B551011/KorService1/areaBasedList1")
                .queryParam("serviceKey", serviceKey)
                .queryParam("numOfRows", 100)
                .queryParam("pageNo", 1)
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "RadoTrip")
                .queryParam("_type", "json")
                .queryParam("listYN", "Y")
                .queryParam("arrange", "A")
                .queryParam("areaCode", areaCode)
                .queryParam("sigunguCode", sigunguCode)
                .queryParam("contentTypeId", contentTypeId)
                .build(true) // true: 인코딩된 상태 유지
                .toUri();

        log.info("Requesting TourAPI: {}", uri);

        try {
            // RestClient 로부터 JSON 문자열을 그대로 받아 반환합니다.
            return restClient.get()
                    .uri(uri)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(String.class);
        } catch (Exception e) {
            log.error("Failed to fetch data from TourAPI", e);
            throw new RuntimeException("TourAPI 연동 실패: " + e.getMessage(), e);
        }
    }
}
