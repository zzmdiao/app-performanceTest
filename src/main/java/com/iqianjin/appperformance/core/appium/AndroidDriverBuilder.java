package com.iqianjin.appperformance.core.appium;

import com.iqianjin.appperformance.core.MobileDevice;
import com.iqianjin.appperformance.core.PortProvider;
import com.iqianjin.appperformance.core.android.AndroidDevice;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.util.StringUtils;

public class AndroidDriverBuilder implements AppiumDriverBuilder {

    public static String APP_PACKAGE = "io.appium.android.apis";
    public static String APP_ACTIVITY = "io.appium.android.apis.ApiDemos";

    @Override
    public AppiumDriver build(MobileDevice mobileDevice, boolean isFirstBuild) {
        // http://appium.io/docs/en/writing-running-appium/caps/
        DesiredCapabilities capabilities = createDesiredCapabilities(mobileDevice);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        capabilities.setCapability(AndroidMobileCapabilityType.UNICODE_KEYBOARD, true); // 切换到appium输入法

        if (((AndroidDevice) mobileDevice).canUseUiautomator2()) {
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2); // UIAutomation2 is only supported since Android 5.0 (Lollipop)
        } else {
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UIAutomator1");
        }

        capabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, PortProvider.getUiautomator2ServerAvailablePort());
        capabilities.setCapability("chromedriverPort", PortProvider.getChromeDriverAvailablePort());
        capabilities.setCapability("showChromedriverLog", true);
        capabilities.setCapability("extractChromeAndroidPackageFromContextName", true);
        String chromeDriverFilePath = AndroidDevice.getChromeDriverFilePath();
        if (!StringUtils.isEmpty(chromeDriverFilePath)) {
            capabilities.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE, chromeDriverFilePath);
        }

        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, APP_PACKAGE);
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, APP_ACTIVITY);
        capabilities.setCapability(AndroidMobileCapabilityType.NO_SIGN, true);
        // https://appium.io/docs/en/writing-running-appium/caps/ 解锁屏幕
        capabilities.setCapability("unlockType", "password");
        capabilities.setCapability("unlockKey", "1111");

        if (!isFirstBuild) {
            // 不是第一次初始化appium，skip掉一些操作，可以提升初始化driver的速度
            capabilities.setCapability("skipServerInstallation", true);
            capabilities.setCapability("skipDeviceInitialization", true);
            capabilities.setCapability("skipUnlock", true);
        }
        capabilities.setCapability("autoLaunch", false);
        capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
        capabilities.setCapability("skipLogcatCapture", true);

        return new AndroidDriver(mobileDevice.getAppiumServer().getUrl(), capabilities);
    }
}
