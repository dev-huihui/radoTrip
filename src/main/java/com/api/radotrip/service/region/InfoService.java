package com.api.radotrip.service.region;

import com.api.radotrip.client.TourApiClient;
import com.api.radotrip.dto.region.ClassificationDto;
import com.api.radotrip.dto.region.InfoDto;
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
public class InfoService {
    private final TourApiClient tourApiClient;
    private final InfoMapper infoMapper;

    /**
     * API 로부터 전국 지역 정보를 받아 파싱하고, DB에 저장한다.
     * 
     * @return 파싱·저장된 레코드 수 (현재는 파싱된 DTO 개수)
     */
    @Transactional
    public int addRegionInfo() {
        String json = tourApiClient.fetchRegionInfo();
        // 2026.06.26 데이터가 없을 경우
        if (json == null || json.isBlank()) {
            log.warn("AreaInfo API returned empty response");
            return 0;
        }

        List<InfoDto> dtos = CommonUtil.parseToList("", json, InfoDto.class);
        int retVal = 0;
        // 2026.06.26 데이터 추가
        for (InfoDto dto : dtos) {
            retVal += infoMapper.addRegionInfo(dto);
        }

        return retVal;
    }

    /**
     * API 로부터 분류체계 코드를 받아 파싱하고, DB에 저장한다.
     *
     * @return 파싱·저장된 레코드 수
     */
    @Transactional
    public int addClassificationInfo() {
        String json = tourApiClient.fetchLclsSystemCd();
        if (json == null || json.isBlank()) {
            log.warn("ClassificationCode API returned empty response");
            return 0;
        }

        List<ClassificationDto> dtos = CommonUtil.parseToList("", json, ClassificationDto.class);
        int retVal = 0;
        for (ClassificationDto dto : dtos) {
            retVal += infoMapper.addClassificationInfo(dto);
        }

        return retVal;
    }
}