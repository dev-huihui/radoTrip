package com.api.radotrip.client;

// Removed TourApiResponse import as we will return raw JSON
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import org.springframework.web.util.UriComponentsBuilder;

import com.api.radotrip.util.CommonUtil;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
    public String fetchJeonjuFestivalInfo(String startDate, String endDate) {
        // serviceKey는 이미 인코딩된 키이므로 build(true)로 보존하고,
        // 한글 파라미터는 직접 URL 인코딩해서 넣는다.
        String region = URLEncoder.encode("전북특별자치도", StandardCharsets.UTF_8);
        String keyword = URLEncoder.encode("영화제", StandardCharsets.UTF_8);
        URI uri = UriComponentsBuilder.fromUriString(baseUrl + "/B553457/cultureinfo/area2")
                .queryParam("serviceKey", serviceKey)
                .queryParam("numOfRows", 10)
                .queryParam("pageNo", 1)
                .queryParam("sido", region)
                .queryParam("from", startDate)
                .queryParam("to", endDate)
                .queryParam("keyword", keyword)
                // 주의: 이 API(B553457/cultureinfo)는 _type=json 을 지원하지 않으며(XML 전용),
                //       해당 파라미터를 넣으면 406이 반환된다. 응답은 XML로 받는다.
                .build(true) // true: 인코딩된 상태 유지
                .toUri();

        log.info("Requesting TourAPI: {}", uri);

        try {
            // 일부 공공데이터 API는 Accept 헤더를 만족하지 못하면 406을 반환하므로,
            // 형식을 강제하지 않고 응답 본문을 그대로 받는다.
            // 이 API의 응답 Content-Type 에는 charset 표기가 없어 String 변환기가
            // ISO-8859-1 로 디코딩하면 한글이 깨진다. byte[] 로 받아 UTF-8 로 직접 디코딩한다.
            byte[] bytes = restClient.get()
                    .uri(uri)
                    .accept(MediaType.ALL)
                    .retrieve()
                    .body(byte[].class);
            String xml = bytes == null ? "" : new String(bytes, StandardCharsets.UTF_8);
            // 이 API는 XML 전용이므로, 후속 JSON 파싱을 위해 JSON 문자열로 변환해 반환한다.
            return CommonUtil.xmlToJson(xml);
        } catch (Exception e) {
            log.error("Failed to fetch data from TourAPI", e);
            throw new RuntimeException("Jeonju festival 연동 실패: " + e.getMessage(), e);
        }
    }

    // 2026.06.26 전국 지역정보 가져오기
    public String fetchRegionInfo() {
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

    // 2026.06.30 지역 축제정보 가져오기
    public String fetchRegionFestival(String lDongRegnCd, String lDongSignguCd, String startDate) {
        // 공공데이터포털 API Key는 디코딩된 키인 경우가 많아 URI 인코딩 처리가 깨지지 않도록 URI 객체 직접 빌드
        URI uri = UriComponentsBuilder.fromUriString(baseUrl + "/B551011/KorService2/searchFestival2")
                .queryParam("serviceKey", serviceKey)
                .queryParam("numOfRows", 9999)
                .queryParam("pageNo", 1)
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "RadoTrip")
                .queryParam("_type", "json")
                .queryParam("eventStartDate", startDate)
                .queryParam("lDongRegnCd", lDongRegnCd)
                .queryParam("lDongSignguCd", lDongSignguCd)
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
