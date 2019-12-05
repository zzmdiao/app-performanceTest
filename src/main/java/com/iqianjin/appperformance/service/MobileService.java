package com.iqianjin.appperformance.service;

import com.iqianjin.appperformance.core.MobileDevice;
import com.iqianjin.appperformance.core.MobileDeviceHolder;
import com.iqianjin.lego.contracts.Result;
import io.appium.java_client.AppiumDriver;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Slf4j
@Service
public class MobileService {

    public Result dump(String deviceId) {
        MobileDevice mobileDevice = MobileDeviceHolder.getConnectedDevice(deviceId);
        if (mobileDevice == null) {
            return Result.failure(-1,"设备未连接");
        }
        if (!mobileDevice.isNativeContext()) {
            return Result.ok(mobileDevice.getAppiumDriver().getPageSource());
        }

        try {
            String pageSource = mobileDevice.dump();
            return Result.ok( pageSource);
        } catch (DocumentException e) {
            log.error("读取pageSource出错", e);
            return Result.failure(-1,"读取pageSource出错，请稍后重试");
        } catch (IOException e) {
            log.error("io err", e);
            return Result.failure(-1,e.getMessage());
        }
    }
    public AppiumDriver freshDriver(String deviceId) {
        MobileDevice mobileDevice = MobileDeviceHolder.getConnectedDevice(deviceId);
        if (mobileDevice == null) {
            log.error("没有链接的设备：{}");
            return null;
        }

        return mobileDevice.freshAppiumDriver();
    }

    public Result installApp(MultipartFile app, String deviceId) {
        MobileDevice mobileDevice = MobileDeviceHolder.getConnectedDevice(deviceId);
        if (mobileDevice == null) {
            return Result.failure(-1,"设备未连接");
        }
        try {
            mobileDevice.installApp(app);
            return Result.ok("安装成功");
        } catch (Exception e) {
            log.error("安装app失败", e);
            return Result.failure(-1,e.getMessage());
        }
    }

}
