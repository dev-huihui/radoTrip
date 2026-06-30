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
            // 2026.06.30 분류체계 코드 테이블에서 숙박 유형(명칭) 조회 (소분류 우선, 없으면 중분류)
            dto.setAccomType(infoMapper.loadClassificationName(dto.getLclsSystm2(), dto.getLclsSystm3()));
            // 2026.06.30 지역ID 가져오기
            dto.setRegnId(infoMapper.loadRegionId("accom", null, dto.getRegion(), dto.getSigungu()));
            retVal += accommodationMapper.addAccommodationInfo(dto);
        }

        return retVal;
    }
}
