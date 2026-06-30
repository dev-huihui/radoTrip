package com.api.radotrip.service.region;

import java.util.List;

import com.api.radotrip.mapper.region.InfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.radotrip.client.TourApiClient;
import com.api.radotrip.dto.region.FestivalDto;
import com.api.radotrip.mapper.region.FestivalMapper;
import com.api.radotrip.util.CommonUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FestivalService {
    private final TourApiClient tourApiClient;
    private final FestivalMapper festivalMapper;
    private final InfoMapper infoMapper;

    /**
     * API 로부터 축제/영화제 정보를 받아 파싱하고, DB에 저장한다.
     *
     * @return 파싱·저장된 레코드 수
     */
    @Transactional
    public int addFestivalInfo(String apiType) {
        String startDate = CommonUtil.getNowYear() + "0101";
        String endDate = CommonUtil.getNowYear() + "1231";
        String json = "";

        if (apiType.equals("JIFF")) {
            // 2026.06.30 전주국제영화제 정보
            json = tourApiClient.fetchJeonjuFestivalInfo(startDate, endDate);
        } else {
            // 2026.06.30 전북특별자치도 축제정보
            json = tourApiClient.fetchRegionFestival("52", "", startDate);
        }

        // 데이터가 없을 경우
        if (json == null || json.isBlank()) {
            log.warn("FestivalInfo API returned empty response");
            return 0;
        }

        List<FestivalDto> dtos = CommonUtil.parseToList(apiType, json, FestivalDto.class);
        int retVal = 0;
        // 데이터 추가
        for (FestivalDto dto : dtos) {
            // 2026.06.30 전주국제영화제일 경우 homepage 주소 넣기
            if (apiType.equals("JIFF")) dto.setHomepageUrl("https://www.jeonjufest.kr/");
            dto.setApiType(apiType);
            // 2026.06.30 지역ID 가져오기
            dto.setRegnId(infoMapper.loadRegionId("festival", apiType, dto.getRegion(), dto.getSigungu()));
            retVal += festivalMapper.addFestivalInfo(dto);
        }

        return retVal;
    }
}
