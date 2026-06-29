package com.api.radotrip.controller.area;

import com.api.radotrip.service.area.InfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/area/info")
@RequiredArgsConstructor
public class InfoController {
    private final InfoService infoService;

    @PostMapping("/fetch")
    public ResponseEntity<String> fetchAndSave() {
        int inserted = infoService.addAreaInfo();
        return ResponseEntity.ok("Inserted " + inserted + " records.");
    }

}
