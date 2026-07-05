# 🎬 라도 트립 (Rado Trip) - 전주국제영화제 특화 버전 (API Project)

> 전주국제영화제(JIFF) 방문객을 위한 축제 일정 중심의 AI 기반 여행 일정 및 동선 최적화 추천 웹 서비스 

---

## 1. 프로젝트 개요 (Project Overview)

### 1.1 기획 배경 및 필요성
매년 4-5월 개최되는 전주국제영화제(JIFF)는 수많은 관람객이 찾는 대표적인 축제입니다. 그러나 많은 방문객이 영화 상영 시간 외에 전주 및 인근 전북 지역의 관광, 맛집 정보를 파악하는 데 어려움을 겪고 있습니다.
**'라도 트립'**은 분산되어 있는 영화 상영 정보와 지역 관광 데이터를 결합하여, 동선 낭비 없는 최적의 여행 일정을 자동으로 생성하고 방문객의 체류 만족도를 극대화하기 위해 기획되었습니다.

### 1.2 핵심 가치 (Core Value)
- **통합성**: 영화 상영 정보, 지역 관광지, 맛집, 날씨 정보를 하나의 서비스에서 제공.
- **효율성**: 고정된 영화 관람 시간을 중심으로 반경 내 최적의 이동 동선 설계.
- **지역 상생**: 전주 10미(味) 및 전북 인근 권역 축제(임실, 순창 등)를 연계하여 다채로운 경험 제안.

---

## 2. 주요 기능 (Key Features)

### 🗺️ 축제 및 영화 스마트 검색
- 날짜(달력 피커), 장소, 키워드 기반의 전주국제영화제 상영작 및 시간표 검색.
- 영화관 위치 및 상영 시간대별 실시간 필터링 기능 제공.

### 📍 Anchor(고정점) 기반 주변 장소 자동 탐색
- 유저가 선택한 영화 상영 시간 및 상영관 위치를 고정점(Anchor)으로 설정.
- 설정된 Anchor 기준 반경 (2km ~ 5km) 이내의 관광지, 맛집, 숙박 업소를 실시간 수집 및 추천.
- '전주 10미(비빔밥, 막걸리 골목 등)' 특화 태그 필터링 제공.

### ⏱️ 지능형 일정 자동 생성 및 편집 (TSP 알고리즘)
- 유저가 설정한 N박 M일 일정에 맞춰 실시간 타임라인 자동 생성.
- 외판원 순회 알고리즘(TSP) 및 지도 API 연동을 통한 도보/대중교통 최단 동선 시각화.
- 드래그 앤 드롭(Drag & Drop)을 통한 자유로운 일정 순서 변경 및 실시간 지도 리렌더링 (단, 영화 상영 시간 카드는 고정되어 앞뒤 일정만 유연하게 조정 가능).

### 🎈 전북 광역 축제 연계 추천
- 전주 내 서비스에 머무르지 않고, 동일 기간 개최되는 전북 인근 지역(임실 치즈 축제, 순창 고추장 축제 등) 연계 추천.
- 유저 동의 시 여행의 마지막 날 일정 등에 인근 축제 동선을 결합하는 팝업/배너 인터랙션 제공.

---

## 3. 기술 스택 (Tech Stack)

### Front-End
- **Framework**: Next.js (App Router) 
- **Styling**: Tailwind CSS 
- **State/Interaction**: @dnd-kit (or React Beautiful DND) — 일정 편집 인터랙션 구현용 

### Back-End & Data
- **Database/ORM**: MySQL
- **Environment**: API Key 및 환경 변수 통합 관리 (보안 및 개인정보 보호) 

### External APIs (데이터 파이프라인)
- **한국관광공사 TourAPI 4.0**: 국문 관광정보, 음식점, 숙박 데이터 및 좌표(mapx, mapy) 수집.
- **JIFF 공식 데이터**: 영화별 상영 시간 및 영화관 위치 데이터.
- **기상청 단기예보 API**: 일정별 강수 확률 및 실시간 날씨 데이터 연동.
- **지도 및 로컬 API**: 카카오맵 API (동선 시각화 및 최단 거리 계산), 네이버 지역 검색 API (맛집 데이터 보완).

---

## 4. 프로젝트 구조 (Project Structure)

```text
radoTripApi/
├── src/
│   ├── main/
│   │   ├── java/com/api/radotrip/
│   │   │   ├── RadoTripApiApplication.java
│   │   │   ├── ServletInitializer.java
│   │   │   ├── client/
│   │   │   │   └── TourApiClient.java               # 한국관광공사 TourAPI 호출 (지역/축제/관광지/음식점/숙박/분류코드)
│   │   │   ├── config/
│   │   │   │   ├── RestClientConfig.java            # RestClient 빈 설정
│   │   │   │   └── SwaggerConfig.java               # Swagger UI 설정
│   │   │   ├── controller/
│   │   │   │   └── region/
│   │   │   │       ├── InfoController.java          # POST /api/region/info/fetch, /api/region/info/classification/fetch
│   │   │   │       ├── FestivalController.java      # POST /api/region/festival/fetch, /api/region/festival/jeonjuFetch
│   │   │   │       ├── TourismController.java       # POST /api/region/tourism/tourFetch, /api/region/tourism/foodFetch
│   │   │   │       └── AccommodationController.java # POST /api/region/accomm/fetch
│   │   │   ├── dto/
│   │   │   │   └── region/
│   │   │   │       ├── InfoDto.java                 # 지역 코드 (region_code)
│   │   │   │       ├── ClassificationDto.java       # 분류체계 코드 (classification_code)
│   │   │   │       ├── FestivalDto.java             # 축제/영화제 (festival_info)
│   │   │   │       ├── TourismDto.java              # 관광지/음식점 (tourism_info)
│   │   │   │       └── AccommodationDto.java        # 숙박 (accommodation_info)
│   │   │   ├── mapper/
│   │   │   │   └── region/
│   │   │   │       ├── InfoMapper.java
│   │   │   │       ├── FestivalMapper.java
│   │   │   │       ├── TourismMapper.java
│   │   │   │       └── AccommodationMapper.java
│   │   │   ├── schedule/
│   │   │   │   └── ScheduleRepo.java                # 데이터 자동 수집 스케줄러 (매일 새벽 순차 실행)
│   │   │   ├── service/
│   │   │   │   └── region/
│   │   │   │       ├── InfoService.java             # 지역 코드 / 분류코드 수집·저장
│   │   │   │       ├── FestivalService.java         # 전북 축제 / 전주국제영화제(JIFF) 수집·저장
│   │   │   │       ├── TourismService.java          # 관광지 / 음식점 수집·저장
│   │   │   │       └── AccommodationService.java    # 숙박 수집·저장
│   │   │   └── util/
│   │   │       ├── CommonUtil.java                  # XML→JSON 변환, 공통 JSON 파싱
│   │   │       └── PasswordEncryptor.java
│   │   └── resources/
│   │       ├── application.yaml
│   │       ├── application-local.yaml
│   │       ├── application-dev.yaml
│   │       └── mappers/
│   │           ├── InfoMapper.xml
│   │           ├── FestivalMapper.xml
│   │           ├── TourismMapper.xml
│   │           └── AccommodationMapper.xml
│   └── test/
├── build.gradle
├── settings.gradle
├── gradlew / gradlew.bat
├── Table.md                                          # DB 테이블 생성 DDL
└── README.md
```

## 5. 개발 환경 설정

### JDK 경로 설정 (gradle.properties)
루트의 `gradle.properties` 파일에서 Gradle 빌드에 사용할 JDK 경로를 지정합니다.

```properties
org.gradle.java.home=C:/Program Files/Java/jdk-21.0.2
```

- 이 경로는 **개발자 본인 PC에 설치된 JDK 21 경로**에 맞게 수정해서 사용합니다.
- JDK 설치 위치가 다르면 빌드가 실패하므로, 클론 후 본인 환경의 JDK 21 설치 경로로 변경해 주세요.

---

## 6. API 확인(Swagger)
### 접속방법
http://localhost:8181/swagger 여기로 접속해서 확인