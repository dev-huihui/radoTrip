package com.api.radotrip.mapper.region;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.api.radotrip.dto.region.ClassificationDto;
import com.api.radotrip.dto.region.InfoDto;

@Mapper
public interface InfoMapper {
    int addRegionInfo(InfoDto infoDto);

    int addClassificationInfo(ClassificationDto classificationDto);

    Long loadRegionId(@Param("type") String type,
                      @Param("apiType") String apiType,
                      @Param("region") String region,
                      @Param("sigungu") String sigungu);
}
