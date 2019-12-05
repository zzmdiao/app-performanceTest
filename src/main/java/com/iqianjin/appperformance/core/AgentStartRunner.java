package com.iqianjin.appperformance.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iqianjin.appperformance.core.android.ADB;
import com.iqianjin.appperformance.core.android.AndroidDeviceChangeListener;
import com.iqianjin.appperformance.core.appium.AppiumServer;
import com.iqianjin.appperformance.core.ios.IosDeviceChangeListener;
import com.iqianjin.appperformance.core.ios.IosDeviceMonitor;
import com.iqianjin.appperformance.util.TerminalUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;


@Slf4j
@Component
public class AgentStartRunner implements ApplicationRunner {

    /**
     * todo 补充文档
     * 防止切换webview报错
     * 1. chrome://inspect 查看手机webview chrome版本
     * 2. https://chromedriver.storage.googleapis.com/2.41/notes.txt 找到手机匹配的chromedriver版本
     * 3. https://npm.taobao.org/mirrors/chromedriver 下载相应版本的chromedriver
     * 文件编码utf-8
     * eg. {"deviceIdA": "/path/chromedriver", "deviceIdB": "c:/path/chromedriver.exe"}
     */
    private static final String DEVICE_CHROMEDRIVER_JSON_FILE = "device_chromedriver.json";

    /**
     * deviceId : chromeDriverFilePath
     */
    private static JSONObject deviceIdChromeDriverFilePath;

    @Autowired
    private AndroidDeviceChangeListener androidDeviceChangeListener;
    @Autowired
    private IosDeviceChangeListener iosDeviceChangeListener;

    @Value("${android}")
    private boolean needAndroid;
    @Value("${ios}")
    private boolean needIos;

    @Value("${uploadAndroidResultPath}")
    private String uploadAndroidResultPath;
    @Value("${iosIfusePath}")
    private String iosIfusePath;
    @Value("${web.upload-img-path}")
    private String uploadImgPath;
    @Value("${web.upload-video-path}")
    private String uploadVideoPath;
    @Value("${web.upload-apk-path}")
    private String uploadApkPath;
    @Value("${web.upload-ipa-path}")
    private String uploadIpaPath;

    @Override
    public void run(ApplicationArguments args) throws IOException, InterruptedException {
        if (needAndroid) {
            ADB.killServer();
            Thread.sleep(1000);
            ADB.startServer();
            ADB.addDeviceChangeListener(androidDeviceChangeListener);
            log.info("[android]开始监听设备连接/断开");
        } else {
            log.info("[android]未开启Android功能");
        }

        if (needIos) {
            IosDeviceMonitor iosDeviceMonitor = IosDeviceMonitor.getInstance();
            iosDeviceMonitor.start(iosDeviceChangeListener);
            log.info("[ios]开始监听设备连接/断开");
        } else {
            log.info("[ios]未开启ios功能");
        }

        File deviceChromeDriverJsonFile = new File(DEVICE_CHROMEDRIVER_JSON_FILE);
        if (deviceChromeDriverJsonFile.exists()) {
            log.info("检测到{}", DEVICE_CHROMEDRIVER_JSON_FILE);
            String deviceChromeDriverJsonFileContent = FileUtils.readFileToString(deviceChromeDriverJsonFile, Charset.forName("UTF-8"));
            log.info("{} => {}", DEVICE_CHROMEDRIVER_JSON_FILE, deviceChromeDriverJsonFileContent);
            deviceIdChromeDriverFilePath = JSON.parseObject(deviceChromeDriverJsonFileContent);
        }

        // appium版本
        String appiumVersion = AppiumServer.getVersion();
        System.setProperty("appiumVersion", appiumVersion);

        // 是否配置了aapt
        String aaptVersion = TerminalUtils.execute("aapt v");
        if (!StringUtils.isEmpty(aaptVersion) && aaptVersion.startsWith("Android")) {
            System.setProperty("aapt", "true");
        } else {
            System.setProperty("aapt", "false");
        }

        File uploadAndroidResultDir = new File(uploadAndroidResultPath);
        if (!uploadAndroidResultDir.exists()) {
            log.info("创建android性能测试结果上传存放目录 -> {}", uploadAndroidResultPath);
            uploadAndroidResultDir.mkdirs();
        }

        File iosIfuseDir = new File(iosIfusePath);
        if (!iosIfuseDir.exists()) {
            log.info("创建ios性能测试挂载目录 -> {}", iosIfusePath);
            iosIfuseDir.mkdirs();
        }

        File uploadImgDir = new File(uploadImgPath);
        if (!uploadImgDir.exists()) {
            log.info("创建图片上传存放目录 -> {}", uploadImgPath);
            uploadImgDir.mkdirs();
        }

        File uploadVideoDir = new File(uploadVideoPath);
        if (!uploadVideoDir.exists()) {
            log.info("创建视频上传存放目录 -> {}", uploadVideoPath);
            uploadVideoDir.mkdirs();
        }

        File uploadApkDir = new File(uploadApkPath);
        if (!uploadApkDir.exists()) {
            log.info("创建apk上传存放目录 -> {}", uploadApkPath);
            uploadApkDir.mkdirs();
        }

        File uploadIpaDir = new File(uploadIpaPath);
        if (!uploadIpaDir.exists()) {
            log.info("创建ipa上传存放目录 -> {}", uploadIpaDir);
            uploadIpaDir.mkdirs();
        }

    }

    public static String getChromeDriverFilePath(String deviceId) {
        return deviceIdChromeDriverFilePath == null ? null : deviceIdChromeDriverFilePath.getString(deviceId);
    }
}
