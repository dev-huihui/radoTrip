package com.api.radotrip.mapper.region;

import com.api.radotrip.dto.region.FestivalDto;
import org.apache.ibatis.annotations.Mapper;

import com.api.radotrip.dto.region.InfoDto;

@Mapper
public interface InfoMapper {
    int addRegionInfo(InfoDto infoDto);

    Long loadRegionId(FestivalDto festivalDto);
}
