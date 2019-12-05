package com.iqianjin.appperformance.service;

import com.iqianjin.appperformance.App;
import com.iqianjin.appperformance.db.mapper.DeviceMapper;
import com.iqianjin.appperformance.db.model.Device;
import com.iqianjin.lego.contracts.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
@Slf4j
public class DeviceService {
    private static DeviceService deviceService;

    @Resource
    private DeviceMapper deviceMapper;

    public synchronized static DeviceService getInstance() {
        if (deviceService == null) {
            deviceService = App.getBean(DeviceService.class);
        }
        return deviceService;
    }
    /**
     * 保存手机信息
     *
     * @param device
     * @return
     */
    public Result save(Device device) {
        // 数据库是否有该手机
        Device dbDevice = deviceMapper.selectByPrimaryKey(device.getId());
        int saveRow;
        if (dbDevice == null) {
            // 首次接入的device
            saveRow = deviceMapper.insert(device);
        } else {
            // 更新Device
            saveRow = deviceMapper.updateByPrimaryKey(device);
        }
        return saveRow == 1 ? Result.ok("保存成功") : Result.failure(-1, "保存失败，请稍后重试");
    }
    /**
     * 通过设备id获取Device
     *
     * @return
     */
    public Device getDeviceById(String deviceId) {
        return deviceMapper.selectByPrimaryKey(deviceId);
    }

}
