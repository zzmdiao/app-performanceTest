package com.iqianjin.appperformance.core.appium;

import com.iqianjin.appperformance.App;
import com.iqianjin.appperformance.core.MobileDevice;
import com.iqianjin.appperformance.core.PortProvider;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.util.StringUtils;


public class IosDriverBuilder implements AppiumDriverBuilder {

    public static  String BUNDLE_ID = "com.apple.mobilesafari";

    @Override
    public AppiumDriver build(MobileDevice mobileDevice, boolean isFirstBuild) {
        // https://github.com/appium/appium-xcuitest-driver
        DesiredCapabilities capabilities = createDesiredCapabilities(mobileDevice);
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true); // http://appium.io/docs/en/writing-running-appium/other/reset-strategies/index.html
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
        capabilities.setCapability(IOSMobileCapabilityType.WDA_LOCAL_PORT, PortProvider.getWdaLocalAvailablePort());
        capabilities.setCapability(IOSMobileCapabilityType.WDA_STARTUP_RETRY_INTERVAL, 60000);
        capabilities.setCapability(IOSMobileCapabilityType.WDA_STARTUP_RETRIES, 4);
        capabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, BUNDLE_ID);
        capabilities.setCapability(IOSMobileCapabilityType.START_IWDP, true);
        capabilities.setCapability("mjpegServerPort", PortProvider.getWdaMjpegServerAvailablePort());
        capabilities.setCapability("waitForQuiescence", false);
        capabilities.setCapability("skipLogCapture", true);
        // Get JSON source from WDA and parse into XML on Appium server. This can be much faster, especially on large devices.
        capabilities.setCapability("useJSONSource", true);
        // http://appium.io/docs/en/advanced-concepts/settings/
        capabilities.setCapability("mjpegServerFramerate", Integer.parseInt(App.getProperty("mjpegServerFramerate")));
        capabilities.setCapability("webkitDebugProxyPort", PortProvider.getWebkitDebugProxyAvalilablePort());

        // https://github.com/appium/appium-xcuitest-driver/blob/master/docs/real-device-config.md
        String xcodeOrgId = App.getProperty("xcodeOrgId");
        if (!StringUtils.isEmpty(xcodeOrgId)) {
            capabilities.setCapability("xcodeOrgId", xcodeOrgId);
        }
        String xcodeSigningId = App.getProperty("xcodeSigningId");
        if (!StringUtils.isEmpty(xcodeSigningId)) {
            capabilities.setCapability("xcodeSigningId", xcodeSigningId);
        }
        String updatedWDABundleId = App.getProperty("updatedWDABundleId");
        if (!StringUtils.isEmpty(updatedWDABundleId)) {
            capabilities.setCapability("updatedWDABundleId", updatedWDABundleId);
        }

        IOSDriver iosDriver = new IOSDriver(mobileDevice.getAppiumServer().getUrl(), capabilities);
//        IosUtil.pressHome(iosDriver);
        return iosDriver;
    }
}
