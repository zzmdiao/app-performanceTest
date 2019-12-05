package com.iqianjin.appperformance.db.mapper;

import com.iqianjin.appperformance.db.model.ElementLocation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElementLocationMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ElementLocation record);

    int insertSelective(ElementLocation record);

    ElementLocation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ElementLocation record);

    int updateByPrimaryKey(ElementLocation record);

    List<ElementLocation> selectElementList();
    String selectByNameAndPlatform(@Param("name") String name, @Param("platform") Integer platform);


}
