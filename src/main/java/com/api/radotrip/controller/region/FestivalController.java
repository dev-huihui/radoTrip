package com.api.radotrip.controller.region;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.radotrip.service.region.FestivalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/region/festival")
@RequiredArgsConstructor
public class FestivalController {
    private final FestivalService festivalService;

    @PostMapping("/jeonjuFetch")
    public ResponseEntity<String> fetchAndSaveForJeonju() {
        int inserted = festivalService.addFestivalInfo("JIFF");
        return ResponseEntity.ok("Inserted " + inserted + " records.");
    }

    @PostMapping("/fetch")
    public ResponseEntity<String> fetchAndSave() {
        int inserted = festivalService.addFestivalInfo("PUB");
        return ResponseEntity.ok("Inserted " + inserted + " records.");
    }

}
