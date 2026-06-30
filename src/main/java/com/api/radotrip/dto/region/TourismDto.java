package com.api.radotrip.dto.region;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TourismDto {
    private Long regnId;

    @JsonProperty("contentid")
    private String contentId;

    // cultureinfo/area2 응답 필드 매핑 (한국문화정보원 공연전시정보)
    @JsonProperty("title")
    private String tourNm;
    private String tourType;

    // place: 공연/축제 장소명, area+sigungu: 지역
    @JsonProperty("addr1")
    private String addr1;
    @JsonProperty("addr2")
    private String addr2;

    @JsonProperty("lDongRegnCd")
    private String region;
    @JsonProperty("lDongSignguCd")
    private String sigungu;

    @JsonAlias("mapx")
    private Double mapx;
    @JsonAlias("mapy")
    private Double mapy;

    @JsonProperty("tel")
    private String tel;

    @JsonAlias("firstimage")
    private String thumbnail;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
