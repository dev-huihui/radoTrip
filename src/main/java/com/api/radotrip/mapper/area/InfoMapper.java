package com.api.radotrip.mapper.area;

import org.apache.ibatis.annotations.Mapper;

import com.api.radotrip.dto.area.InfoDto;

@Mapper
public interface InfoMapper {
    int insertAreaInfo(InfoDto infoDto);
}
