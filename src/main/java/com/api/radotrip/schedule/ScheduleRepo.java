package com.api.radotrip.schedule;

import com.api.radotrip.service.region.FestivalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.api.radotrip.service.region.InfoService;

/**
 * 프로젝트 전체 스케줄러를 한곳에 모아 관리한다.
 * 현재는 전국 지역 정보를 매일 02:00에 수집한다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduleRepo {

    private final InfoService infoService;

    private final FestivalService festivalService;

    /** 매일 02:00에 실행 */
    @Scheduled(cron = "${schedule.regionInfo}")
    public void runRegionInfoJob() {
        log.info("===== RegionInfoScheduler START =====");
        try {
            int saved = infoService.addRegionInfo();
            log.info("[Scheduler] RegionInfo 저장 완료 – {} 레코드", saved);
        } catch (Exception e) {
            log.error("[Scheduler] RegionInfoScheduler 실행 중 오류", e);
        }
        log.info("===== RegionInfoScheduler END =====");
    }

    @Scheduled(cron = "${schedule.jeonjuMovieInfo}")
    public void runJeonjuMovieInfoJob() {
        log.info("===== JeonjuMovieInfoScheduler START =====");
        try {
            int saved = festivalService.addFestivalInfo();
            log.info("[Scheduler] JeonjuMovieInfo 저장 완료 – {} 레코드", saved);
        } catch (Exception e) {
            log.error("[Scheduler] JeonjuMovieInfoScheduler 실행 중 오류", e);
        }
        log.info("===== JeonjuMovieInfoScheduler END =====");
    }
}
