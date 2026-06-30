package com.api.radotrip.service.region;

import com.api.radotrip.client.TourApiClient;
import com.api.radotrip.dto.region.AccommodationDto;
import com.api.radotrip.mapper.region.AccommodationMapper;
import com.api.radotrip.mapper.region.InfoMapper;
import com.api.radotrip.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccommodationService {
    private final TourApiClient tourApiClient;
    private final AccommodationMapper accommodationMapper;
    private final InfoMapper infoMapper;

    /**
     * API 로부터 숙박 정보를 받아 파싱하고, DB에 저장한다.
     *
     * @return 파싱·저장된 레코드 수
     */
    @Transactional
    public int addAccommodationInfo() {

        // 2026.06.30 전북특별자치도 숙박정보
        String json = tourApiClient.fetchRegionAccom("52", "");

        // 데이터가 없을 경우
        if (json == null || json.isBlank()) {
            log.warn("AccommodationInfo API returned empty response");
            return 0;
        }

        List<AccommodationDto> dtos = CommonUtil.parseToList("", json, AccommodationDto.class);
        int retVal = 0;
        // 데이터 추가
        for (AccommodationDto dto : dtos) {
            // 2026.06.30 분류체계로 숙박 유형 결정
            dto.setAccomType(resolveAccomType(dto.getLclsSystm2(), dto.getLclsSystm3()));
            // 2026.06.30 지역ID 가져오기
            dto.setRegnId(infoMapper.loadRegionId("accom", null, dto.getRegion(), dto.getSigungu()));
            retVal += accommodationMapper.addAccommodationInfo(dto);
        }

        return retVal;
    }

    /**
     * TourAPI 분류체계 코드로 숙박 유형(ACCOM_TYPE)을 결정한다.
     * 한옥스테이/게스트하우스 등은 소분류(lclsSystm3)에서만 구분되므로 소분류를 우선 사용하고,
     * 소분류로 판별이 안 되면 중분류(lclsSystm2)로 대략 분류한다.
     */
    private String resolveAccomType(String lclsSystm2, String lclsSystm3) {
        // 소분류(lclsSystm3) 우선
        if (lclsSystm3 != null) {
            String type = switch (lclsSystm3) {
                case "AC010100" -> "HOTEL";     // 호텔
                case "AC020100" -> "CONDO";     // 콘도
                case "AC020200" -> "RESIDENCE"; // 레지던스
                case "AC030100" -> "PENSION";   // 펜션
                case "AC030200" -> "HANOK";     // 한옥스테이
                case "AC030300" -> "MINBAK";    // 농어촌민박
                case "AC030400" -> "HOMESTAY";  // 홈스테이
                case "AC040100" -> "MOTEL";     // 모텔
                case "AC060100" -> "HOSTEL";    // 유스호스텔
                case "AC060200" -> "GUEST";     // 게스트하우스
                case "VE050200" -> "RESORT";    // 리조트
                default -> null;
            };
            if (type != null) return type;
        }
        // 소분류로 못 정하면 중분류(lclsSystm2)로 폴백
        if (lclsSystm2 != null) {
            return switch (lclsSystm2) {
                case "AC01" -> "HOTEL";   // 호텔
                case "AC02" -> "CONDO";   // 콘도미니엄
                case "AC03" -> "PENSION"; // 펜션/민박
                case "AC04" -> "MOTEL";   // 모텔
                case "AC06" -> "HOSTEL";  // 호스텔
                case "VE05" -> "RESORT";  // 복합관광시설
                default -> "ETC";
            };
        }
        return "ETC";
    }
}
