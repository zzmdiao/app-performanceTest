package com.iqianjin.appperformance.core.ios;

import com.google.common.collect.ImmutableMap;
import com.iqianjin.appperformance.util.TerminalUtils;
import io.appium.java_client.AppiumDriver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Slf4j
public class IosUtil {

    public static List<String> getDeviceList() {
        try {
            String result = TerminalUtils.execute("idevice_id -l");
            if (StringUtils.isEmpty(result)) {
                return Collections.emptyList();
            }
            return Arrays.asList(result.split("\n"));
        } catch (IOException e) {
            log.error("execute 'idevice_id -l' err", e);
            return Collections.emptyList();
        }
    }

    /**
     * @param deviceId
     * @return eg. 10.3.4
     * @throws IOException
     */
    public static String getSystemVersion(String deviceId) throws IOException {
        return TerminalUtils.execute("ideviceinfo -k ProductVersion -u " + deviceId);
    }

    /**
     * @param deviceId
     * @return eg. iPhone5,2
     * @throws IOException
     */
    public static String getProductType(String deviceId) throws IOException {
        return TerminalUtils.execute("ideviceinfo -k ProductType -u " + deviceId);
    }

    public static String getDeviceName(String deviceId) throws IOException {
        return TerminalUtils.execute("ideviceinfo -k DeviceName -u " + deviceId);
    }

    public static File screenshotByIdeviceScreenshot(String deviceId) throws IOException {
        // Screenshot saved to screenshot-xxx.png
        String result = TerminalUtils.execute("idevicescreenshot -u " + deviceId);
        if (StringUtils.isEmpty(result)) {
            throw new RuntimeException("截图失败，idevicescreenshot返回空");
        }
        String[] resultArr = result.split(" ");
        return new File(resultArr[resultArr.length - 1].replaceAll("\n", ""));
    }

    // http://appium.io/docs/en/commands/mobile-command/
    public static void pressHome(AppiumDriver appiumDriver) {
        appiumDriver.executeScript("mobile:pressButton", ImmutableMap.of("name", "home"));
    }

    public static void installIpa(String ipaPath, String deviceId) throws IOException {
        TerminalUtils.execute("ideviceinstaller -i " + ipaPath + " -u " + deviceId);
    }

    public static void uninstallApp(AppiumDriver appiumDriver, String bundleId) {
        appiumDriver.executeScript("mobile: removeApp", ImmutableMap.of("bundleId", bundleId));
    }

    public static void launchApp(AppiumDriver appiumDriver, String bundleId) {
        appiumDriver.executeScript("mobile: launchApp", ImmutableMap.of("bundleId", bundleId));
    }

    public static void activeAPP(AppiumDriver appiumDriver){
        appiumDriver.activateApp(appiumDriver.getCapabilities().getCapability("bundleId").toString());
    }

    // Terminates an existing application on the device. If the application is not running then the returned result will be false, otherwise true
    public static boolean terminateApp(AppiumDriver appiumDriver, String bundleId) {
        return (Boolean) appiumDriver.executeScript("mobile: terminateApp", ImmutableMap.of("bundleId", bundleId));
    }

    /**
     * 获取ios app版本号，依赖libimobiledevice
     * @return
     */
    public static String getAppVersion(){
        String version = null;
        try {
            version= TerminalUtils.execute("ideviceinstaller -l |grep 'com.iqianjin.iqianjinplus' | awk '{print $4}'| awk -F '.' '{print $1\".\"$2\".\"$3}'");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * brew cask install osxfuse
     * brew install ifuse
     * 借助ifuse将沙盒挂载到本地
     * //https://www.cnblogs.com/zhouxihi/p/10369580.html
     * @return
     */
    public static void setIosIfuse(){
        String shpath = System.getProperty("user.dir") + "/Upload/Shell/setIOSIfuse.sh";
        String resultPath = System.getProperty("user.dir") + "/Upload/IosResult/";
        log.info("ios开始挂载沙盒到本地目录：{}",resultPath);
        try {
            TerminalUtils.execute("/bin/sh "+ shpath +" "+ resultPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
