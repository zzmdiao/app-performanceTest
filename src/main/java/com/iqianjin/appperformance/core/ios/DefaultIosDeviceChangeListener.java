package com.iqianjin.appperformance.core.ios;

import com.iqianjin.appperformance.core.MobileDevice;
import com.iqianjin.appperformance.core.MobileDeviceHolder;
import com.iqianjin.appperformance.core.appium.AppiumServer;
import com.iqianjin.appperformance.db.mapper.DeviceMapper;
import com.iqianjin.appperformance.db.model.Device;
import com.iqianjin.appperformance.service.DeviceService;
import io.appium.java_client.AppiumDriver;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Dimension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;


@Slf4j
@Component
public class DefaultIosDeviceChangeListener implements IosDeviceChangeListener {

    @Resource
    private DeviceMapper deviceMapper;
    @Autowired
    private DeviceService deviceService;

    @Override
    public void onDeviceConnected(String deviceId) {
        new Thread(() -> iosDeviceConnected(deviceId)).start();
    }

    @Override
    public void onDeviceDisconnected(String deviceId) {
        new Thread(() -> iosDeviceDisconnected(deviceId)).start();
    }

    private void iosDeviceConnected(String deviceId) {
        log.info("[ios][{}]已连接", deviceId);

        MobileDevice mobileDevice = MobileDeviceHolder.get(deviceId);
        if (mobileDevice == null) {
            log.info("[ios][{}]首次在线", deviceId);

            log.info("[ios][{}]启动appium server...", deviceId);
            AppiumServer appiumServer = new AppiumServer();
            appiumServer.start();
            log.info("[ios][{}]启动appium server完成，url: {}", deviceId, appiumServer.getUrl());

            log.info("[ios][{}]检查是否已接入过", deviceId);
            Device device = deviceMapper.selectByPrimaryKey(deviceId);
            if (device == null) {
                log.info("[ios][{}]首次接入，开始初始化设备", deviceId);
                try {
                    mobileDevice = initIosDevice(deviceId, appiumServer);
                    log.info("[ios][{}]初始化设备完成", deviceId);
                } catch (Exception e) {
                    appiumServer.stop();
                    throw new RuntimeException("初始化设备" + deviceId + "出错", e);
                }
            } else {
                log.info("[ios][{}]已接入过", deviceId);
                mobileDevice = new IosDevice(device, appiumServer);
            }

            MobileDeviceHolder.add(deviceId, mobileDevice);
            log.info("UDID是:{}", MobileDeviceHolder.get(deviceId).getId());
        } else {
            log.info("[ios][{}]非首次在线", deviceId);
        }
    }

    private void iosDeviceDisconnected(String deviceId) {
        log.info("[ios][{}]断开连接", deviceId);
        MobileDevice mobileDevice = MobileDeviceHolder.get(deviceId);
        if (mobileDevice == null) {
            return;
        }

        log.info("[ios][{}]iosDeviceDisconnected处理完成", deviceId);
    }

    private MobileDevice initIosDevice(String deviceId, AppiumServer appiumServer) throws Exception {
        Device device = new Device();

        device.setPlatform(MobileDevice.IOS);
        device.setCreateTime(new Date());
        device.setId(deviceId);
        device.setSystemVersion(IosUtil.getSystemVersion(deviceId));
        device.setName(IosUtil.getDeviceName(deviceId));

        String msg = "请根据productType：" + IosUtil.getProductType(deviceId) + "查出相应的信息，补充到device表";
        device.setCpuInfo(msg);
        device.setMemSize(msg);

        IosDevice iosDevice = new IosDevice(device, appiumServer);

        log.info("[ios][{}]开始初始化appium", deviceId);
        AppiumDriver appiumDriver = iosDevice.initAppiumDriver();
        log.info("[ios][{}]初始化appium完成", deviceId);

        // 有时window获取的宽高可能为0
        while (true) {
            Dimension window = appiumDriver.manage().window().getSize();
            int width = window.getWidth();
            int height = window.getHeight();

            if (width > 0 && height > 0) {
                device.setScreenWidth(window.getWidth());
                device.setScreenHeight(window.getHeight());
                break;
            } else {
                log.warn("[ios][{}]未获取到正确的屏幕宽高: {}", deviceId, window);
            }
        }
        device.setStatus(Device.IDLE_STATUS);
        device.setLastOnlineTime(new Date());
        deviceService.save(device);

        appiumDriver.quit();
        return iosDevice;
    }
}
