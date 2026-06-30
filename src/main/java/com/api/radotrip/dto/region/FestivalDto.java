package com.api.radotrip.dto.region;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FestivalDto {
    private Long regnId;

    // cultureinfo/area2 응답 필드 매핑 (한국문화정보원 공연전시정보)
    @JsonProperty("title")
    private String festNm;

    @JsonAlias({"startDate", "eventstartdate"})
    private String eventStartDate;
    @JsonAlias({"endDate", "eventenddate"})
    private String eventEndDate;

    // place: 공연/축제 장소명, area+sigungu: 지역
    @JsonAlias({"place", "addr1"})
    private String addr1;
    @JsonProperty("addr2")
    private String addr2;

    @JsonAlias({"area", "lDongRegnCd"})
    private String region;
    @JsonAlias({"sigungu", "lDongSignguCd"})
    private String sigungu;

    @JsonAlias({"gpsX", "mapx"})
    private Double mapx;
    @JsonAlias({"gpsY", "mapy"})
    private Double mapy;

    @JsonProperty("tel")
    private String tel;

    @JsonAlias({"firstimage", "thumbnail"})
    private String thumbnail;

    private String homepageUrl;

    // API 원천 구분값 (서비스단에서 세팅)
    private String apiType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
