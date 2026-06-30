3CREATE TABLE region_info (
regn_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '지역 ID',
regn_cd VARCHAR(10) NOT NULL COMMENT '시도 코드',
regn_nm VARCHAR(50) NOT NULL COMMENT '시도 명칭',
signgu_cd VARCHAR(10) NOT NULL COMMENT '시군구 코드',
signgu_nm VARCHAR(50) NOT NULL COMMENT '시군구 명칭',
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
UNIQUE KEY uq_region_code (regn_cd, signgu_cd)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='지역 정보 테이블';

-- radotrip.festival_info definition

CREATE TABLE `festival_info` (
`fest_id` int NOT NULL AUTO_INCREMENT COMMENT '축제 ID',
`regn_id` bigint NOT NULL COMMENT '지역 정보 ID',
`content_id` varchar(500) DEFAULT NULL COMMENT '콘텐츠ID',
`fest_nm` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '축제 명칭',
`event_start_date` date NOT NULL COMMENT '축제 시작일',
`event_end_date` date NOT NULL COMMENT '축제 종료일',
`addr1` varchar(255) NOT NULL COMMENT '대표 주소',
`addr2` varchar(255) DEFAULT NULL COMMENT '상세 주소',
`mapx` decimal(13,10) DEFAULT NULL COMMENT '경도 (GPS X좌표)',
`mapy` decimal(13,10) DEFAULT NULL COMMENT '위도 (GPS Y좌표)',
`tel` varchar(50) DEFAULT NULL COMMENT '축제 문의처 전화번호',
`thumbnail` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '대표 포스터 이미지 URL',
`homepage_url` varchar(500) DEFAULT NULL COMMENT '공식 홈페이지 링크',
`api_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'API' COMMENT '데이터 출처 (API 또는 MANUAL)',
`lclsSystm1` varchar(100) DEFAULT NULL COMMENT '대분류',
`lclsSystm2` varchar(100) DEFAULT NULL COMMENT '중분류',
`lclsSystm3` varchar(100) DEFAULT NULL COMMENT '소분류',
`created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
`updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY (`fest_id`),
KEY `regn_id` (`regn_id`),
UNIQUE KEY `uk_festival_content` (`content_id`, `api_type`),
CONSTRAINT `festival_info_ibfk_1` FOREIGN KEY (`regn_id`) REFERENCES `region_info` (`regn_id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='지역 축제 및 영화제 마스터 정보 테이블';


-- radotrip.tourism_info definition

CREATE TABLE `tourism_info` (
`tour_id` int NOT NULL AUTO_INCREMENT COMMENT '관광지 ID',
`regn_id` bigint NOT NULL COMMENT '지역 정보 ID',
`content_id` varchar(500) DEFAULT NULL COMMENT '콘텐츠ID',
`tour_nm` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '관광지/영화관 명칭',
`tour_type` varchar(50) NOT NULL COMMENT '분류 (THEATER: 영화관, ATTRACTION: 일반관광지, FOOD: 맛집)',
`addr1` varchar(255) NOT NULL COMMENT '대표 주소',
`addr2` varchar(255) DEFAULT NULL COMMENT '상세 주소',
`mapx` decimal(13,10) DEFAULT NULL COMMENT '경도 (GPS X좌표)',
`mapy` decimal(13,10) DEFAULT NULL COMMENT '위도 (GPS Y좌표)',
`tel` varchar(50) DEFAULT NULL COMMENT '전화번호',
`thumbnail` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '대표 이미지 URL',
`overview` text COMMENT '상세 개요/소개 내용',
`lclsSystm1` varchar(100) DEFAULT NULL COMMENT '대분류',
`lclsSystm2` varchar(100) DEFAULT NULL COMMENT '중분류',
`lclsSystm3` varchar(100) DEFAULT NULL COMMENT '소분류',
`created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
`updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY (`tour_id`),
KEY `regn_id` (`regn_id`),
UNIQUE KEY `uk_tourism_content` (`content_id`),
CONSTRAINT `tourism_info_ibfk_1` FOREIGN KEY (`regn_id`) REFERENCES `region_info` (`regn_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1594 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='관광 명소 및 상영관 정보 테이블';

-- radotrip.accommodation_info definition

CREATE TABLE `accommodation_info` (
`accom_id` int NOT NULL AUTO_INCREMENT COMMENT '숙박 ID',
`regn_id` bigint NOT NULL COMMENT '지역 정보 ID',
`content_id` varchar(500) DEFAULT NULL COMMENT '콘텐츠ID',
`accom_nm` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '숙소 명칭',
`accom_type` varchar(50) DEFAULT NULL COMMENT '숙박 분류 (HANOK: 한옥스테이, HOTEL: 호텔, GUEST: 게스타하우스)',
`addr1` varchar(255) NOT NULL COMMENT '대표 주소',
`addr2` varchar(255) DEFAULT NULL COMMENT '상세 주소',
`mapx` decimal(13,10) DEFAULT NULL COMMENT '경도 (GPS X좌표)',
`mapy` decimal(13,10) DEFAULT NULL COMMENT '위도 (GPS Y좌표)',
`tel` varchar(50) DEFAULT NULL COMMENT '예약/문의 전화번호',
`thumbnail` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '숙소 대표 이미지 URL',
`lclsSystm1` varchar(100) DEFAULT NULL COMMENT '대분류',
`lclsSystm2` varchar(100) DEFAULT NULL COMMENT '중분류',
`lclsSystm3` varchar(100) DEFAULT NULL COMMENT '소분류',
`created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
`updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY (`accom_id`),
KEY `regn_id` (`regn_id`),
UNIQUE KEY `uk_accom_content` (`content_id`),
CONSTRAINT `accommodation_info_ibfk_1` FOREIGN KEY (`regn_id`) REFERENCES `region_info` (`regn_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='숙박 시설 정보 테이블';

CREATE TABLE classification_code (
clsf_id      INT AUTO_INCREMENT PRIMARY KEY COMMENT '분류코드 ID',
lclsSystm1Cd VARCHAR(20)  COMMENT '대분류 코드',
lclsSystm1Nm VARCHAR(100) COMMENT '대분류 명',
lclsSystm2Cd VARCHAR(20)  COMMENT '중분류 코드',
lclsSystm2Nm VARCHAR(100) COMMENT '중분류 명',
lclsSystm3Cd VARCHAR(20)  COMMENT '소분류 코드',
lclsSystm3Nm VARCHAR(100) COMMENT '소분류 명',
created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
UNIQUE KEY uk_lcls_systm3 (lclsSystm3Cd)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='분류체계 코드 테이블';


