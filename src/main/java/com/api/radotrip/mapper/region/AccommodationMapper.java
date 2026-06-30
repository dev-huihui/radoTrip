package com.api.radotrip.mapper.region;

import com.api.radotrip.dto.region.AccommodationDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccommodationMapper {
    int addAccommodationInfo(AccommodationDto accommodationDto);
}
