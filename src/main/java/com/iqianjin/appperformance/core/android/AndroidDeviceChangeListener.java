package com.iqianjin.appperformance.core.android;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;
import com.iqianjin.appperformance.core.MobileDevice;
import com.iqianjin.appperformance.core.MobileDeviceHolder;
import com.iqianjin.appperformance.core.appium.AppiumServer;
import com.iqianjin.appperformance.db.model.Device;
import com.iqianjin.appperformance.service.DeviceService;
import io.appium.java_client.AppiumDriver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;

@Component
@Slf4j
public class AndroidDeviceChangeListener implements AndroidDebugBridge.IDeviceChangeListener {

    @Autowired
    private DeviceService deviceService;

    @Override
    public void deviceConnected(IDevice device) {
        new Thread(() -> androidDeviceConnected(device)).start();
    }

    @Override
    public void deviceDisconnected(IDevice device) {
        new Thread(() -> androidDeviceDisconnected(device)).start();
    }

    @Override
    public void deviceChanged(IDevice device, int changeMask) {
    }

    /**
     * Android设备连接到电脑
     *
     * @param iDevice
     */
    private void androidDeviceConnected(IDevice iDevice) {
        String deviceId = iDevice.getSerialNumber();
        log.info("[android][{}]已连接", deviceId);

        log.info("[android][{}]等待手机上线", deviceId);
        AndroidUtil.waitForDeviceOnline(iDevice, 5 * 60);
        log.info("[android][{}]手机已上线", deviceId);

        MobileDevice mobileDevice = MobileDeviceHolder.get(deviceId);
        if (mobileDevice == null) {
            log.info("[android][{}]首次上线", deviceId);

            log.info("[android][{}]启动appium server...", deviceId);
            AppiumServer appiumServer = new AppiumServer();
            appiumServer.start();
            log.info("[android][{}]启动appium server完成，url: {}", deviceId, appiumServer.getUrl());

            try {
                mobileDevice = initAndroidDevice(iDevice, appiumServer);
                log.info("[android][{}]初始化设备完成", deviceId);
            } catch (Exception e) {
                appiumServer.stop();
                throw new RuntimeException("初始化设备" + deviceId + "出错", e);
            }

            MobileDeviceHolder.add(deviceId, mobileDevice);
        } else {
            ((AndroidDevice) mobileDevice).setIDevice(iDevice);
            log.info("[android][{}]非首次上线", deviceId);
        }

        log.info("[android][{}]androidDeviceConnected处理完成", deviceId);
    }

    /**
     * Android设备断开电脑
     *
     * @param iDevice
     */
    public void androidDeviceDisconnected(IDevice iDevice) {
        String deviceId = iDevice.getSerialNumber();
        log.info("[android][{}]断开连接", deviceId);
        MobileDevice mobileDevice = MobileDeviceHolder.get(deviceId);
        if (mobileDevice == null) {
            return;
        }
        mobileDevice.saveOfflineDevice();
        log.info("[android][{}]androidDeviceDisconnected处理完成", deviceId);
    }

    /**
     * 首次接入系统，初始化Android设备
     */
    private MobileDevice initAndroidDevice(IDevice iDevice, AppiumServer appiumServer) throws Exception {
        String deviceId = iDevice.getSerialNumber();
        Device device = new Device();

        device.setPlatform(MobileDevice.ANDROID);
        device.setCreateTime(new Date());
        device.setId(deviceId);
        device.setSystemVersion(AndroidUtil.getAndroidVersion(iDevice));
        device.setName(AndroidUtil.getDeviceName(iDevice));

        try {
            device.setCpuInfo(AndroidUtil.getCpuInfo(iDevice));
        } catch (Exception e) {
            log.error("获取cpu信息失败", e);
            device.setCpuInfo("获取cpu信息失败");
        }
        try {
            device.setMemSize(AndroidUtil.getMemSize(iDevice));
        } catch (Exception e) {
            log.error("获取内存大小失败", e);
            device.setMemSize("获取内存大小失败");
        }

        AndroidDevice androidDevice = new AndroidDevice(device, iDevice, appiumServer);

        // 安装一个测试apk，用于初始化appium driver
        androidDevice.installApp(new File("Upload/apks/ApiDemos-debug.apk"));

        log.info("[android][{}]开始初始化appium", deviceId);
        AppiumDriver appiumDriver = androidDevice.initAppiumDriver();
        log.info("[android][{}]初始化appium完成", deviceId);

        String resolution = AndroidUtil.getResolution(iDevice); // 720x1280
        String[] res = resolution.split("x");
        device.setScreenWidth(Integer.parseInt(res[0]));
        device.setScreenHeight(Integer.parseInt(res[1]));

        device.setStatus(Device.IDLE_STATUS);
        device.setLastOnlineTime(new Date());
        deviceService.save(device);
        appiumDriver.quit();

        return androidDevice;
    }
}
