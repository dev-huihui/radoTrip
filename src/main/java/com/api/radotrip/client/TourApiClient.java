package com.api.radotrip.client;

import com.api.radotrip.dto.tour.TourApiResponse;
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

    @Value("${tour-api.service-key:YOUR_SERVICE_KEY}")
    private String serviceKey;

    @Value("${tour-api.base-url:https://apis.data.go.kr/B551011/KorService1}")
    private String baseUrl;

    /**
     * 지역 기반 관광정보 조회 (areaBasedList1)
     *
     * @param areaCode      지역코드 (예: 전북은 37)
     * @param sigunguCode   시군구코드 (예: 전주는 12)
     * @param contentTypeId 관광지 타입 (12: 관광지, 14: 문화시설, 32: 숙박, 39: 음식점 등)
     * @return TourApiResponse
     */
    public TourApiResponse fetchAreaBasedList(String areaCode, String sigunguCode, String contentTypeId) {
        // 공공데이터포털 API Key는 디코딩된 키인 경우가 많아 URI 인코딩 처리가 깨지지 않도록 URI 객체 직접 빌드
        URI uri = UriComponentsBuilder.fromUriString(baseUrl + "/areaBasedList1")
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
            return restClient.get()
                    .uri(uri)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(TourApiResponse.class);
        } catch (Exception e) {
            log.error("Failed to fetch data from TourAPI", e);
            throw new RuntimeException("TourAPI 연동 실패: " + e.getMessage(), e);
        }
    }
}
