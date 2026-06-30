package com.api.radotrip.schedule;

import com.api.radotrip.service.region.AccommodationService;
import com.api.radotrip.service.region.FestivalService;
import com.api.radotrip.service.region.TourismService;
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
    private final TourismService tourismService;
    private final AccommodationService accommodationService;

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
            int saved = festivalService.addFestivalInfo("JIFF");
            log.info("[Scheduler] JeonjuMovieInfo 저장 완료 – {} 레코드", saved);
        } catch (Exception e) {
            log.error("[Scheduler] JeonjuMovieInfoScheduler 실행 중 오류", e);
        }
        log.info("===== JeonjuMovieInfoScheduler END =====");
    }

    @Scheduled(cron = "${schedule.festivalInfo}")
    public void runFestivalInfoJob() {
        log.info("===== FestivalInfoScheduler START =====");
        try {
            int saved = festivalService.addFestivalInfo("PUB");
            log.info("[Scheduler] FestivalInfo 저장 완료 – {} 레코드", saved);
        } catch (Exception e) {
            log.error("[Scheduler] FestivalInfoScheduler 실행 중 오류", e);
        }
        log.info("===== FestivalInfoScheduler END =====");
    }

    @Scheduled(cron = "${schedule.tourInfo}")
    public void runTourInfoJob() {
        log.info("===== TourInfoScheduler START =====");
        try {
            int saved = tourismService.addTourismInfo("12");
            log.info("[Scheduler] TourInfo 저장 완료 – {} 레코드", saved);
        } catch (Exception e) {
            log.error("[Scheduler] TourInfoScheduler 실행 중 오류", e);
        }
        log.info("===== TourInfoScheduler END =====");
    }

    @Scheduled(cron = "${schedule.foodInfo}")
    public void runFoodInfoJob() {
        log.info("===== FoodInfoScheduler START =====");
        try {
            int saved = tourismService.addTourismInfo("39");
            log.info("[Scheduler] FoodInfo 저장 완료 – {} 레코드", saved);
        } catch (Exception e) {
            log.error("[Scheduler] FoodInfoScheduler 실행 중 오류", e);
        }
        log.info("===== FoodInfoScheduler END =====");
    }

    @Scheduled(cron = "${schedule.accommInfo}")
    public void runAccommInfoJob() {
        log.info("===== AccommInfoScheduler START =====");
        try {
            int saved = accommodationService.addAccommodationInfo();
            log.info("[Scheduler] AccommInfo 저장 완료 – {} 레코드", saved);
        } catch (Exception e) {
            log.error("[Scheduler] AccommInfoScheduler 실행 중 오류", e);
        }
        log.info("===== AccommInfoScheduler END =====");
    }

    @Scheduled(cron = "${schedule.classificationInfo}")
    public void runClassificationInfoJob() {
        log.info("===== ClassificationInfoScheduler START =====");
        try {
            int saved = infoService.addClassificationInfo();
            log.info("[Scheduler] ClassificationInfo 저장 완료 – {} 레코드", saved);
        } catch (Exception e) {
            log.error("[Scheduler] ClassificationInfoScheduler 실행 중 오류", e);
        }
        log.info("===== ClassificationInfoScheduler END =====");
    }
}
