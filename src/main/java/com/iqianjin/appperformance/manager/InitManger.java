package com.iqianjin.appperformance.manager;

import com.iqianjin.appperformance.core.BaseAction;
import com.iqianjin.appperformance.core.MobileDevice;
import com.iqianjin.appperformance.core.MobileDeviceHolder;
import com.iqianjin.appperformance.core.ios.IosUtil;
import com.iqianjin.appperformance.db.model.Device;
import com.iqianjin.appperformance.service.DeviceService;
import com.iqianjin.appperformance.service.MobileService;
import com.iqianjin.appperformance.util.FileMUtils;
import com.iqianjin.appperformance.util.TerminalUtils;
import com.iqianjin.lego.contracts.Result;
import io.appium.java_client.AppiumDriver;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.iqianjin.appperformance.manager.GetStartTime.getAndroidStartTime2;

@Component
@Slf4j
public class InitManger {

    private AppiumDriver appiumDriver;
    private Integer platform;
    private MobileDevice mobileDevice;

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private MobileService mobileService;
    @Autowired
    private BaseAction baseAction;
    @Autowired
    private LoginManager loginManager;
    @Autowired
    private UploadManager uploadManager;

    private String dFloatingWindow = "doraemon悬浮窗";
    private String dCustomButton = "doraemon自定义";
    private String dDataCollection = "doraemon数据采集按钮";
    private String dDataClose = "android悬浮窗退出";

    //切换环境
    private String xiaolian = "笑脸";
    private String xiaolianEnvi = "环境";
    private String xiaolianEnviInput = "环境输入框";
    private String xiaolianButton = "确定替换服务器";
    private String otherLogin = "其他登录";

    public String guideBottomStartTv = "开始使用爱钱进";
    public String guideStartBtn = "跳过";
    private String homeTab = "首页tab";
    private String productTab = "产品tab";
    private String myTab = "我的tab";

    public Result init(String deviceId, String env, String userName, String passWord) {
        try {

            mobileDevice = MobileDeviceHolder.get(deviceId);
            if (!mobileDevice.isIdle()) {
                log.error("手机正在使用中");
                return Result.failure(-2, "手机正在使用中");
            }
            mobileDevice.saveUsingDevice();
            //获得当前手机类型 Android or ios
            platform = deviceService.getDeviceById(deviceId).getPlatform();
            //新建appiumDriver
            appiumDriver = mobileService.freshDriver(deviceId);

            baseAction.setAppiumDriver(appiumDriver);
            baseAction.setPlatform(platform);

            //启动app
            if (MobileDevice.ANDROID == platform) {
                appiumDriver.launchApp();
            }
            changeEnv(env);
            log.info("[自动化测试][{}], 开始录制视频...", deviceId);
            mobileDevice.startRecordingScreen();
            //登录
            loginManager.login(userName, passWord);
            //开启监控
            startAndroidMonitor();
            startIosMonitor();
            return Result.ok();
        } catch (Exception e) {
            log.error("初始化出错，将手机设置空闲状态:{}", e);
            mobileDevice.saveIdelDevice();
            return Result.failure(-2, "初始化出错");
        }


    }

    public void afterAll(String[] statements) {
        try {
            log.info("更新手机状态为：{}", Device.IDLE_STATUS);
            mobileDevice.saveIdelDevice();
            log.info("停止录制视频");
            mobileDevice.stopRecordingScreenAndUpload();
            TerminalUtils.sleep(5);
            log.info("退出appiumDriver");
            mobileDevice.quitAppiumDriver();
        } catch (Exception e) {
            log.error("afterAll错误:{}", e);
        } finally {
            //上完文件到平台
            //todo：ios启动时间暂时写死
            int startTime = 1700;
            if (MobileDevice.ANDROID == platform) {
                startTime = getAndroidStartTime2(3);
            }
            UploadManager.getInstance().uploadFile(statements, String.valueOf(startTime));
            //将上传完的文件备份到Result目录
            uploadManager.moveFile(platform);
            baseAction.removeAppiumDriver();
            baseAction.removePlatform();
        }
    }

    public void changeEnv(String envi) {
        log.info("开始切换环境：{}", envi);
        if (MobileDevice.IOS == platform) {
            if (baseAction.isCanFindElement(guideBottomStartTv)) {
                baseAction.click(guideStartBtn);
            }
            baseAction.click(homeTab);
            baseAction.longPreePostion(70, 70);
            baseAction.click(xiaolian);
            baseAction.click(xiaolianEnvi);
            baseAction.sendKeys(xiaolianEnviInput, envi);
            baseAction.click(xiaolianButton);
            if (baseAction.isWebview()) {
                baseAction.clickWebview();
            }
            baseAction.click(productTab);
            baseAction.click(homeTab);
            if (baseAction.isWebview()) {
                baseAction.clickWebview();
            }
            baseAction.longPreePostion(70, 70);
        } else {
            try {
                TerminalUtils.execute("adb shell am start -n com.iqianjin.client/com.iqianjin.client.doraemonkit.StartIpServerActivity -e ip http://" + envi);
                TerminalUtils.sleep(2);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (baseAction.isCanFindElement(guideBottomStartTv)) {
                baseAction.click(guideStartBtn);
            }
            baseAction.click(productTab);
            baseAction.click(myTab);
            baseAction.click(otherLogin);
        }
    }


    public void startIosMonitor() {
        if (MobileDevice.IOS == platform) {
            log.info("ios ---- 开启数据采集");
            baseAction.click(dFloatingWindow);
            baseAction.click(dCustomButton);
            if (baseAction.isCanFindElement(dDataCollection, "数据采集已关闭")) {
                baseAction.click(dDataCollection);
            }
            baseAction.click(dFloatingWindow);
            //设置挂载目录
            IosUtil.setIosIfuse();
            //清除旧文件
            uploadManager.delIosFile();
        }
    }


    public void stopIosMonitor() {
        if (MobileDevice.IOS == platform) {
            log.info("ios ---- 关闭数据采集");
            baseAction.click(dFloatingWindow);
            baseAction.click(dCustomButton);
            if (baseAction.isCanFindElement(dDataCollection, "数据采集已开启")) {
                baseAction.click(dDataCollection);
            }
            baseAction.click(dFloatingWindow);
            TerminalUtils.sleep(2);
        }

    }

    public void startAndroidMonitor() {
        if (MobileDevice.ANDROID == platform) {
            try {
                TerminalUtils.execute("adb shell am start -n com.iqianjin.client/com.didichuxing.doraemonkit.ui.UniversalActivity -e fragment_CONTENT 19");
            } catch (IOException e) {
                e.printStackTrace();
            }
            WebElement elementStart = null;
            try {
                elementStart = appiumDriver.findElementByXPath("//*[@text='开始']");
            } catch (Exception e) {
                log.error("无法查询到元素elementStart");
            }

            if (null != elementStart) {
                log.info("开启监控，当前Android监控状态未开启");
                try {
                    log.info("开启Android监控");
                    baseAction.click(dDataClose);
                    TerminalUtils.execute("adb shell am start -n com.iqianjin.client/com.didichuxing.doraemonkit.ui.UniversalActivity -e fragment_CONTENT 18");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                log.info("开启监控，当前Android监控状态已开启，关闭再开启");
                try {
                    baseAction.click(dDataClose);
                    TerminalUtils.execute("adb shell am start -n com.iqianjin.client/com.didichuxing.doraemonkit.ui.UniversalActivity -e fragment_CONTENT 18");
                    TerminalUtils.execute("adb shell am start -n com.iqianjin.client/com.didichuxing.doraemonkit.ui.UniversalActivity -e fragment_CONTENT 18");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            TerminalUtils.sleep(1);
        }
    }

    public void stopAndroidMonitor() {
        if (MobileDevice.ANDROID == platform) {
            try {
                TerminalUtils.execute("adb shell am start -n com.iqianjin.client/com.didichuxing.doraemonkit.ui.UniversalActivity -e fragment_CONTENT 19");
            } catch (IOException e) {
                e.printStackTrace();
            }
            WebElement elementEnd = null;
            try {
                elementEnd = appiumDriver.findElementByXPath("//*[@text='结束']");
            } catch (Exception e) {
                log.error("无法查询到元素elementEnd");
            }
            if (null != elementEnd) {
                log.info("停止监控，当前Android监控状态未结束");
                try {
                    baseAction.click(dDataClose);
                    TerminalUtils.execute("adb shell am start -n com.iqianjin.client/com.didichuxing.doraemonkit.ui.UniversalActivity -e fragment_CONTENT 18");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            TerminalUtils.sleep(2);
            getAndroidImportFile();
            TerminalUtils.sleep(10);
        }
    }

    //将android最后的结果文件导出
    public void getAndroidImportFile() {
        try {
            log.info("pull android 数据到本地");
            //先清除本地目录下文件
            String shpath1 = System.getProperty("user.dir") + "/Upload/Shell/rmFile.sh";
            String resultPath = System.getProperty("user.dir") + "/Upload/androidResult/";
            TerminalUtils.execute("/bin/sh " + shpath1 + " " + resultPath);

            TerminalUtils.execute("adb pull /sdcard/Android/data/com.iqianjin.client/files/doraemon/result/. ./Upload/androidResult/");
            if (FileMUtils.isFileEnd(resultPath, "hprof")) {
                log.info("开始转换hprof文件");
                String shpath = System.getProperty("user.dir") + "/Upload/Shell/changeFile.sh";
                TerminalUtils.execute("/bin/sh " + shpath + " " + resultPath);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
