package com.api.radotrip.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.api.radotrip.dto.area.InfoDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            JsonNode items = root.path("response").path("body").path("items").path("item");
            if (items.isMissingNode() || !items.isArray()) {
                return Collections.emptyList();
            }
            List<T> result = new ArrayList<>();
            for (JsonNode node : items) {
                // Jackson will map matching JSON fields to the DTO's properties
                T dto = mapper.treeToValue(node, dtoClass);
                // If the DTO has timestamps, fill them here (common for our DTOs)
                if (dto instanceof InfoDto) {
                    InfoDto info = (InfoDto) dto;
                    info.setCreatedAt(LocalDateTime.now());
                    info.setUpdatedAt(LocalDateTime.now());
                }
                result.add(dto);
            }
            return result;
        } catch (Exception e) {
            // In production you would log the exception; here we just return empty list
            return Collections.emptyList();
        }
    }
}
