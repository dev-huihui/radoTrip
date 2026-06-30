package com.api.radotrip.mapper.region;

import org.apache.ibatis.annotations.Mapper;

import com.api.radotrip.dto.region.FestivalDto;

@Mapper
public interface FestivalMapper {
    int insertFestivalInfo(FestivalDto festivalDto);
}
