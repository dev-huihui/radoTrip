package com.api.radotrip.controller.region;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.radotrip.service.region.InfoService;

import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/region/info")
@RequiredArgsConstructor
public class InfoController {
    private final InfoService infoService;

    @PostMapping("/fetch")
    public ResponseEntity<String> fetchAndSave() {
        int inserted = infoService.addRegionInfo();
        return ResponseEntity.ok("Inserted " + inserted + " records.");
    }

}
