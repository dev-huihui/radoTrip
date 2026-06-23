package com.api.radotrip.service;

import com.api.radotrip.client.TourApiClient;
import com.api.radotrip.dto.PlaceDto;
import com.api.radotrip.dto.tour.TourApiResponse;
import com.api.radotrip.mapper.PlaceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceService {

    private final TourApiClient tourApiClient;
    private final PlaceMapper placeMapper;

    /**
     * DB의 모든 장소 목록 조회
     */
    public List<PlaceDto> getAllPlaces() {
        try {
            return placeMapper.selectAllPlaces();
        } catch (Exception e) {
            log.error("Failed to select all places from DB", e);
            // DB 미연결 시 빈 리스트 반환 등으로 임시 처리 가능
            return Collections.emptyList();
        }
    }

    /**
     * TourAPI에서 전주 맛집(areaCode: 37, sigunguCode: 12, contentTypeId: 39) 리스트를 긁어와 DB에 저장
     */
    @Transactional
    public int fetchAndSaveJeonjuRestaurants() {
        // 전북(37), 전주(12), 음식점(39)
        TourApiResponse apiResponse = tourApiClient.fetchAreaBasedList("37", "12", "39");

        if (apiResponse == null || apiResponse.getResponse() == null 
                || apiResponse.getResponse().getBody() == null 
                || apiResponse.getResponse().getBody().getItems() == null) {
            log.warn("TourAPI response holds no items.");
            return 0;
        }

        List<TourApiResponse.Item> items = apiResponse.getResponse().getBody().getItems().getItem();
        if (items == null || items.isEmpty()) {
            return 0;
        }

        int count = 0;
        for (TourApiResponse.Item item : items) {
            PlaceDto dto = PlaceDto.builder()
                    .title(item.getTitle())
                    .address(item.getAddr1())
                    .contentTypeId(item.getContenttypeid())
                    .mapX(item.getMapX())
                    .mapY(item.getMapY())
                    .build();

            try {
                placeMapper.insertPlace(dto);
                count++;
            } catch (Exception e) {
                log.error("Failed to insert place: {}", dto.getTitle(), e);
            }
        }

        return count;
    }
}
