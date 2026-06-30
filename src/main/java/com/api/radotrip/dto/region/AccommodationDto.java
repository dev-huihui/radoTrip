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
public class AccommodationDto {
    private Long regnId;

    @JsonProperty("contentid")
    private String contentId;

    // areaBasedList(숙박) 응답 필드 매핑
    @JsonProperty("title")
    private String accomNm;
    // 숙박 분류 (HANOK/HOTEL/GUEST 등) - 서비스에서 세팅
    private String accomType;

    // area+sigungu: 지역
    private String addr1;
    private String addr2;

    @JsonProperty("lDongRegnCd")
    private String region;
    @JsonProperty("lDongSignguCd")
    private String sigungu;

    private Double mapx;
    private Double mapy;

    private String tel;

    @JsonAlias("firstimage")
    private String thumbnail;

    // 분류체계 (TourAPI lclsSystm1/2/3)
    private String lclsSystm1;
    private String lclsSystm2;
    private String lclsSystm3;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
