package com.api.radotrip.controller;

import com.api.radotrip.dto.PlaceDto;
import com.api.radotrip.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    /**
     * DB에 저장된 모든 장소 목록 조회
     */
    @GetMapping
    public ResponseEntity<List<PlaceDto>> getPlaces() {
        List<PlaceDto> places = placeService.getAllPlaces();
        return ResponseEntity.ok(places);
    }

    /**
     * 한국관광공사 API로부터 전주 맛집 데이터를 가져와 DB에 싱크 맞추기
     */
    @PostMapping("/sync")
    public ResponseEntity<Map<String, Object>> syncJeonjuRestaurants() {
        int syncedCount = placeService.fetchAndSaveJeonjuRestaurants();
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "전주 맛집 동기화 완료",
                "syncedCount", syncedCount
        ));
    }
}
