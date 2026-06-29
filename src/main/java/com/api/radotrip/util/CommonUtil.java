package com.api.radotrip.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.api.radotrip.dto.region.InfoDto;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class CommonUtil {

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
    public static <T> List<T> parseToList(String json, Class<T> dtoClass) {
        try {
            ObjectMapper mapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .registerModule(new JavaTimeModule());

            JsonNode root = mapper.readTree(json);
            JsonNode items = root.path("response").path("body").path("items").path("item");
            // Log.info("parseToList items : {}", items);
            System.out.println("parseToList items : " + items);
            // 2026.06.26 데이터가 없을 경우
            if (items.isMissingNode() || !items.isArray()) {
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
        if (dto instanceof InfoDto) {
            InfoDto info = (InfoDto) dto;
            LocalDateTime now = LocalDateTime.now();
            info.setCreatedAt(now);
            info.setUpdatedAt(now);
        }

        return dto;
    }
}
