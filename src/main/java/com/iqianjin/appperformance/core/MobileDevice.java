package com.iqianjin.appperformance.core;

import com.iqianjin.appperformance.core.appium.AppiumServer;
import com.iqianjin.appperformance.db.model.Device;
import com.iqianjin.appperformance.manager.UploadManager;
import com.iqianjin.appperformance.service.DeviceService;
import com.iqianjin.appperformance.util.UUIDUtils;
import com.iqianjin.lego.contracts.Result;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.touch.offset.PointOption;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.dom4j.DocumentException;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Slf4j
@Data
public abstract class MobileDevice {

    public static final String NATIVE_CONTEXT = "NATIVE_APP";

    public static final int ANDROID = 1;
    public static final int IOS = 2;

    private Device device;

    private AppiumServer appiumServer;
    private AppiumDriver appiumDriver;

    public MobileDevice(Device device, AppiumServer appiumServer) {
        this.device = device;
        this.appiumServer = appiumServer;
    }

    /**
     * 刷新AppiumDriver
     *
     * @return
     */
    public AppiumDriver freshAppiumDriver() {
        quitAppiumDriver();
        appiumDriver = newAppiumDriver();
        return appiumDriver;
    }

    public void quitAppiumDriver() {
        if (appiumDriver != null) {
            // 退出上次的会话
            try {
                appiumDriver.quit();
            } catch (Exception e) {
                // 上次会话可能已经过期，quit会有异常，ignore
            }
        }
    }

    public abstract AppiumDriver newAppiumDriver();

    public abstract AppiumDriver initAppiumDriver();

    public String getId() {
        return device.getId();
    }

    /**
     * 设备是否连接
     *
     * @return
     */
    public boolean isConnected() {
        return device.getStatus() != Device.OFFLINE_STATUS;
    }

    /**
     * 是否在线闲置
     *
     * @return
     */
    public boolean isIdle() {
        return device.getStatus() == Device.IDLE_STATUS;
    }

    /**
     * 获取设备屏幕分辨率
     *
     * @return eg.1080x1920
     */
    public String getResolution() {
        return String.valueOf(device.getScreenWidth()) + "x" + String.valueOf(device.getScreenHeight());
    }

    public String getVirtualResolution(Integer width) {
        int height = getScreenScaledHeight(width);
        return width + "x" + height;
    }

    /**
     * 按照比例计算高度
     *
     * @param width
     * @return
     */
    public int getScreenScaledHeight(int width) {
        int screenHeight = getDevice().getScreenHeight();
        int screenWidth = getDevice().getScreenWidth();
        float scale = screenHeight / (float) screenWidth;
        return (int) (scale * width);
    }

    public PointOption getPointOption(float percentOfX, float percentOfY) {
        int screenWidth = getDevice().getScreenWidth();
        int screenHeight = getDevice().getScreenHeight();
        return PointOption.point((int) (percentOfX * screenWidth), (int) (percentOfY * screenHeight));
    }

    public abstract void installApp(File appFile) throws Exception;

    public void installApp(MultipartFile app) throws Exception {
        File appFile = new File(UUIDUtils.getUUID() + "." + StringUtils.unqualify(app.getOriginalFilename()));
        try {
            FileUtils.copyInputStreamToFile(app.getInputStream(), appFile);
            installApp(appFile);
        } finally {
            FileUtils.deleteQuietly(appFile);
        }
    }

    public abstract String dump() throws IOException, DocumentException;

    /**
     * 当前是否是原生context
     *
     * @return
     */
    public boolean isNativeContext() {
        if (appiumDriver == null) {
            throw new RuntimeException("appiumDriver未初始化");
        }
        return NATIVE_CONTEXT.equals(appiumDriver.getContext());
    }

    public abstract void installApp(String appDownloadUrl) throws Exception;

    public abstract void startRecordingScreen();

    public abstract File stopRecordingScreen() throws Exception;

    public String stopRecordingScreenAndUpload() throws Exception {
        File video = null;
        try {
            video = stopRecordingScreen();
            Result result = UploadManager.getInstance().uploadFile(video);
            String downloadUrl = ((Map<String, String>) (result.getBean())).get("downloadURL");
            return downloadUrl;
        } finally {
            FileUtils.deleteQuietly(video);
        }
    }

    public abstract File screenshot() throws Exception;

    public String screenshotAndUpload() throws Exception {
        File screenshotFile = null;
        try {
            screenshotFile = screenshot();
            Result result = UploadManager.getInstance().uploadFile(screenshotFile);
            String downloadUrl = ((Map<String, String>) (result.getBean())).get("downloadURL");
            return downloadUrl;
        } finally {
            FileUtils.deleteQuietly(screenshotFile);
        }
    }


    public void saveUsingDevice() {
        if (isConnected()) {
            device.setStatus(Device.USING_STATUS);
            log.info("更新手机状态为使用中");
            DeviceService.getInstance().save(device);
        }
    }

    public void saveIdelDevice() {
        if (isConnected()) {
            device.setStatus(Device.IDLE_STATUS);
            log.info("更新手机状态为空闲");
            DeviceService.getInstance().save(device);
        }
    }

    public void saveOfflineDevice() {
        if (isConnected()) {
            device.setStatus(Device.OFFLINE_STATUS);
            device.setLastOfflineTime(new Date());
            log.info("更新手机状态为下线");
            DeviceService.getInstance().save(device);
        }
    }
}
