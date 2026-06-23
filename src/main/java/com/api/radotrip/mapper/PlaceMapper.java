package com.api.radotrip.mapper;

import com.api.radotrip.dto.PlaceDto;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface PlaceMapper {
    List<PlaceDto> selectAllPlaces();
    PlaceDto selectPlaceById(Long placeId);
    void insertPlace(PlaceDto placeDto);
    void deletePlace(Long placeId);
}
