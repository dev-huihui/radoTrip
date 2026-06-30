package com.api.radotrip.service.region;

import com.api.radotrip.client.TourApiClient;
import com.api.radotrip.dto.region.FestivalDto;
import com.api.radotrip.dto.region.TourismDto;
import com.api.radotrip.mapper.region.FestivalMapper;
import com.api.radotrip.mapper.region.InfoMapper;
import com.api.radotrip.mapper.region.TourismMapper;
import com.api.radotrip.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TourismService {
    private final TourApiClient tourApiClient;
    private final TourismMapper tourismMapper;
    private final InfoMapper infoMapper;

    /**
     * API 로부터 관광지/음식점 정보를 받아 파싱하고, DB에 저장한다.
     *
     * @return 파싱·저장된 레코드 수
     */
    @Transactional
    public int addTourismInfo(String contentId) {

        // 2026.06.30 전북특별자치도 관광정보
        String json = tourApiClient.fetchRegionTour("52", "", contentId);

        // 데이터가 없을 경우
        if (json == null || json.isBlank()) {
            log.warn("TourismInfo API returned empty response");
            return 0;
        }

        List<TourismDto> dtos = CommonUtil.parseToList("", json, TourismDto.class);
        int retVal = 0;
        // 데이터 추가
        for (TourismDto dto : dtos) {
            /* 2026.06.30 콘텐츠별 값 세팅
             * ATTRACTION: 일반관광지, FOOD: 맛집*/
            dto.setTourType(contentId.equals("12") ? "ATTRACTION" : "FOOD");
            // 2026.06.30 지역ID 가져오기
            dto.setRegnId(infoMapper.loadRegionId("tour", dto.getRegion(), dto.getSigungu()));
            retVal += tourismMapper.addTourismInfo(dto);
        }

        return retVal;
    }
}
