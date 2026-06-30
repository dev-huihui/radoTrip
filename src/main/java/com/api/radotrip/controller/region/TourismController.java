package com.api.radotrip.controller.region;

import com.api.radotrip.service.region.FestivalService;
import com.api.radotrip.service.region.TourismService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/region/tourism")
@RequiredArgsConstructor
public class TourismController {
    private final TourismService tourismService;

    @PostMapping("/tourFetch")
    public ResponseEntity<String> fetchAndSaveForTour() {
        int inserted = tourismService.addTourismInfo("12");
        return ResponseEntity.ok("Inserted " + inserted + " records.");
    }

    @PostMapping("/foodFetch")
    public ResponseEntity<String> fetchAndSaveForFood() {
        int inserted = tourismService.addTourismInfo("39");
        return ResponseEntity.ok("Inserted " + inserted + " records.");
    }
}
