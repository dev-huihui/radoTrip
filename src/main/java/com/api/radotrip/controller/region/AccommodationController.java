package com.api.radotrip.controller.region;

import com.api.radotrip.service.region.AccommodationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/region/accomm")
@RequiredArgsConstructor
public class AccommodationController {
    private final AccommodationService accommodationService;

    @PostMapping("/fetch")
    public ResponseEntity<String> fetchAndSave() {
        int inserted = accommodationService.addAccommodationInfo();
        return ResponseEntity.ok("Inserted " + inserted + " records.");
    }
}
