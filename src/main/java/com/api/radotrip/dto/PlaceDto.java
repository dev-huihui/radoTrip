package com.api.radotrip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDto {
    private Long placeId;
    private String title;
    private String address;
    private String contentTypeId;
    private Double mapX;
    private Double mapY;
}
