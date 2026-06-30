package com.api.radotrip.service.region;

import java.util.List;

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

    /** 데이터 원천 구분값 (cultureinfo/area2 - 전주국제영화제) */
    private static final String API_TYPE = "JIFF";

    /**
     * API 로부터 축제/영화제 정보를 받아 파싱하고, DB에 저장한다.
     *
     * @return 파싱·저장된 레코드 수
     */
    @Transactional
    public int addFestivalInfo() {
        String startDate = CommonUtil.getNowYear() + "0101";
        String endDate = CommonUtil.getNowYear() + "1231";
        String json = tourApiClient.fetchJeonjuFestivalInfo(startDate, endDate);
        // 데이터가 없을 경우
        if (json == null || json.isBlank()) {
            log.warn("FestivalInfo API returned empty response");
            return 0;
        }

        List<FestivalDto> dtos = CommonUtil.parseToList(json, FestivalDto.class);
        int retVal = 0;
        // 데이터 추가
        for (FestivalDto dto : dtos) {
            dto.setApiType(API_TYPE);
            retVal += festivalMapper.insertFestivalInfo(dto);
        }

        return retVal;
    }
}
