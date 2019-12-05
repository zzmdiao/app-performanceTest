package com.iqianjin.appperformance.core.appium;

import com.iqianjin.appperformance.core.MobileDevice;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;


public interface AppiumDriverBuilder {
    /**
     * 一个session两次命令之间最大允许12小时间隔，超出12小时，session将会被appium server删除
     */
    int NEW_COMMAND_TIMEOUT = 60 * 60 * 12;

    AppiumDriver build(MobileDevice mobileDevice, boolean isFirstBuild);

    default DesiredCapabilities createDesiredCapabilities(MobileDevice mobileDevice) {
        // http://appium.io/docs/en/writing-running-appium/caps/
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, NEW_COMMAND_TIMEOUT);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, mobileDevice.getDevice().getName());
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, mobileDevice.getDevice().getSystemVersion());
        capabilities.setCapability(MobileCapabilityType.UDID, mobileDevice.getId());
        return capabilities;
    }
}
