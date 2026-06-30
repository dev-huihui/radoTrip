package com.api.radotrip.util;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.api.radotrip.dto.region.FestivalDto;
import com.api.radotrip.dto.region.InfoDto;
import com.api.radotrip.dto.region.TourismDto;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class CommonUtil {

    private static final XmlMapper XML_MAPPER = new XmlMapper();
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    /**
     * XML 문자열을 동일 구조의 JSON 문자열로 변환한다.
     * JSON 응답을 지원하지 않는 공공데이터 API(XML 전용)의 응답을
     * 기존 JSON 파싱 로직에서 그대로 사용할 수 있게 해 준다.
     *
     * @param xml 원본 XML 문자열
     * @return 변환된 JSON 문자열 (실패 시 빈 문자열)
     */
    public static String xmlToJson(String xml) {
        if (xml == null || xml.isBlank()) {
            return "";
        }
        try {
            JsonNode node = XML_MAPPER.readTree(xml);
            return JSON_MAPPER.writeValueAsString(node);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Generic parser that converts the JSON array to a {@link List} of the given
     * DTO class.
     * The DTO class must have a no‑args constructor and setters (or
     * Lombok @Builder).
     *
     * @param <T>      the type of DTO to return
     * @param json     raw JSON string from the API
     * @param dtoClass the concrete DTO class
     * @return a list of parsed DTO objects, or an empty list on error
     */
    public static <T> List<T> parseToList(String type, String json, Class<T> dtoClass) {
        try {
            ObjectMapper mapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .registerModule(new JavaTimeModule());

            JsonNode root = mapper.readTree(json);
            JsonNode items = root.path("response").path("body").path("items").path("item");

            // 2026.06.30 전주국제영화제의 경우는 다른 API에서 가져오기 때문에 따로 재처리
            if (type.equals("JIFF")) {
                items = root.path("body").path("items").path("item");
            }

            // 2026.06.26 데이터가 없을 경우 (결과가 1건이면 배열이 아닌 단일 객체로 내려올 수 있다)
            if (items.isMissingNode() || items.isNull()) {
                return Collections.emptyList();
            }
            List<T> result = new ArrayList<>();
            // 2026.06.26 데이터 추가
            if (items.isArray()) {
                // ④ ***배열***인 경우: 각각을 순회하면서 DTO 로 변환
                for (JsonNode node : items) {
                    result.add(convertNode(mapper, node, dtoClass));
                }
            } else {
                // ⑤ ***단일 객체***인 경우: 바로 한 번만 변환하고 리스트에 추가
                result.add(convertNode(mapper, items, dtoClass));
            }
            return result;
        } catch (Exception e) {
            // In production you would log the exception; here we just return empty list
            // log.error("Unrecognized field '{}' while mapping to {}", e.getPropertyName(),
            // dtoClass.getSimpleName(), e);
            return Collections.emptyList();
        }
    }

    /**
     * ⑦ JsonNode → DTO 변환 + InfoDto 전용 타임스탬프 자동 채우기
     * - `mapper.treeToValue(node, dtoClass)` 로 일반 매핑
     * - DTO 가 `InfoDto` 라면 `createdAt/updatedAt` 필드에 현재 시각을 삽입
     */
    private static <T> T convertNode(ObjectMapper mapper, JsonNode node, Class<T> dtoClass) throws Exception {
        T dto = mapper.treeToValue(node, dtoClass);
        LocalDateTime now = LocalDateTime.now();
        if (dto instanceof InfoDto) {
            InfoDto info = (InfoDto) dto;
            info.setCreatedAt(now);
            info.setUpdatedAt(now);
        } else if (dto instanceof FestivalDto) {
            FestivalDto festival = (FestivalDto) dto;
            festival.setCreatedAt(now);
            festival.setUpdatedAt(now);
        } else if (dto instanceof TourismDto) {
            TourismDto tourism = (TourismDto) dto;
            tourism.setCreatedAt(now);
            tourism.setUpdatedAt(now);
        }

        return dto;
    }

    /**
     * 현재 연도를 문자열(yyyy)로 반환한다.
     *
     * @return 현재 연도 (예: "2026")
     */
    public static String getNowYear() {
        return String.valueOf(Year.now().getValue());
    }
}
