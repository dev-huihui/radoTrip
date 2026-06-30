package com.api.radotrip.mapper.region;

import com.api.radotrip.dto.region.TourismDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TourismMapper {
    int addTourismInfo(TourismDto tourismDto);
}
