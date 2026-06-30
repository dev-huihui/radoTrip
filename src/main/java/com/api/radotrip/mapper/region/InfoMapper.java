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

    /** 분류체계 코드로 분류명을 조회한다. 소분류(lclsSystm3) 우선, 없으면 중분류(lclsSystm2). */
    String loadClassificationName(@Param("lclsSystm2Cd") String lclsSystm2Cd,
                                  @Param("lclsSystm3Cd") String lclsSystm3Cd);
}
