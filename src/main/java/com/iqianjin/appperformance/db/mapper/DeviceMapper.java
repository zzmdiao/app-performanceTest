package com.iqianjin.appperformance.db.mapper;

import com.iqianjin.appperformance.db.model.Device;

public interface DeviceMapper {
    int deleteByPrimaryKey(String id);

    int insert(Device record);

    int insertSelective(Device record);

    Device selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Device record);

    int updateByPrimaryKey(Device record);
}
