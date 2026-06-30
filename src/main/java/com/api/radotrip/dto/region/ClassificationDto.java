package com.api.radotrip.dto.region;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClassificationDto {
    // 분류체계 코드 (TourAPI lclsSystmCode2 응답 필드명과 동일)
    private String lclsSystm1Cd; // 대분류 코드
    private String lclsSystm1Nm; // 대분류 명
    private String lclsSystm2Cd; // 중분류 코드
    private String lclsSystm2Nm; // 중분류 명
    private String lclsSystm3Cd; // 소분류 코드
    private String lclsSystm3Nm; // 소분류 명
}
