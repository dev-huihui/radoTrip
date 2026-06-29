package com.api.radotrip.mapper.region;

import org.apache.ibatis.annotations.Mapper;

import com.api.radotrip.dto.region.InfoDto;

@Mapper
public interface InfoMapper {
    int insertRegionInfo(InfoDto infoDto);
}
