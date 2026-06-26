package com.api.radotrip.schedule;

import com.api.radotrip.service.area.InfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 프로젝트 전체 스케줄러를 한곳에 모아 관리한다.
 * 현재는 전국 지역 정보를 매일 02:00에 수집한다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduleRepo {

    private final InfoService infoService;

    /** 매일 02:00에 실행 */
    @Scheduled(cron = "${schedule.areaInfo}")
    public void runAreaInfoJob() {
        log.info("===== AreaInfoScheduler START =====");
        try {
            int saved = infoService.addAreaInfo();
            log.info("[Scheduler] AreaInfo 저장 완료 – {} 레코드", saved);
        } catch (Exception e) {
            log.error("[Scheduler] AreaInfoScheduler 실행 중 오류", e);
        }
        log.info("===== AreaInfoScheduler END =====");
    }
}
