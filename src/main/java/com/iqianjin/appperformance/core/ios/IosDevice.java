package com.iqianjin.appperformance.core.ios;

import com.iqianjin.appperformance.App;
import com.iqianjin.appperformance.core.MobileDevice;
import com.iqianjin.appperformance.core.appium.AppiumServer;
import com.iqianjin.appperformance.core.appium.IosDriverBuilder;
import com.iqianjin.appperformance.core.appium.IosPageSourceHandler;
import com.iqianjin.appperformance.db.model.Device;
import com.iqianjin.appperformance.util.TerminalUtils;
import com.iqianjin.appperformance.util.UUIDUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSStartScreenRecordingOptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.io.FileUtils;
import org.dom4j.DocumentException;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Base64;
import java.util.concurrent.TimeUnit;


@Slf4j
public class IosDevice extends MobileDevice {
    public static final String IQJ_BUNDLE_ID = "com.iqianjin.iqianjinplus";
    /**
     * iproxy localPort remotePort deviceId
     */
    private static final String IPROXY = "iproxy %d %d %s";
    private ExecuteWatchdog mjpegServerIproxyWatchdog;

    public IosDevice(Device device, AppiumServer appiumServer) {
        super(device, appiumServer);
    }

    @Override
    public AppiumDriver newAppiumDriver() {
        IosDriverBuilder.BUNDLE_ID = IQJ_BUNDLE_ID;
        AppiumDriver appiumDriver = new IosDriverBuilder().build(this, false);
        appiumDriver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
        return  appiumDriver;
    }

    @Override
    public AppiumDriver initAppiumDriver() {
        return new IosDriverBuilder().build(this, true);
    }

    @Override
    public void installApp(File app) throws IOException {
        IosUtil.installIpa(app.getAbsolutePath(), getId());
    }


    @Override
    public File screenshot() throws Exception {
        return IosUtil.screenshotByIdeviceScreenshot(getId());
    }


    @Override
    public String dump() throws IOException, DocumentException {
        return new IosPageSourceHandler(getAppiumDriver()).getPageSource();
    }

    @Override
    public void startRecordingScreen() {
        IOSStartScreenRecordingOptions iosOptions = new IOSStartScreenRecordingOptions();
        // The maximum value is 30 minutes.
        iosOptions.withTimeLimit(Duration.ofMinutes(30));
        iosOptions.withFps(10); // default 10
        iosOptions.withVideoQuality(IOSStartScreenRecordingOptions.VideoQuality.LOW);
        iosOptions.withVideoType("libx264");
        ((IOSDriver) getAppiumDriver()).startRecordingScreen(iosOptions);
    }

    @Override
    public File stopRecordingScreen() throws IOException {
        File videoFile = new File(UUIDUtils.getUUID() + ".mp4");
        String base64Video = ((IOSDriver) getAppiumDriver()).stopRecordingScreen();
        FileUtils.writeByteArrayToFile(videoFile, Base64.getDecoder().decode(base64Video), false);
        return videoFile;
    }


    @Override
    public void installApp(String appDownloadUrl) throws Exception {
        if (StringUtils.isEmpty(appDownloadUrl)) {
            throw new RuntimeException("appDownloadUrl cannot be empty");
        }

        // download ipa
        RestTemplate restTemplate = App.getBean(RestTemplate.class);
        byte[] ipaBytes = restTemplate.getForObject(appDownloadUrl, byte[].class);

        File ipa = new File(UUIDUtils.getUUID() + ".ipa");
        try {
            FileUtils.writeByteArrayToFile(ipa, ipaBytes, false);
            // install
            IosUtil.installIpa(ipa.getAbsolutePath(), getId());
        } finally {
            // delete ipa
            FileUtils.deleteQuietly(ipa);
        }
    }

    public int getMjpegServerPort() {
        Object mjpegServerPort = getAppiumDriver().getCapabilities().asMap().get("mjpegServerPort");
        return (int) ((long) mjpegServerPort);
    }

    public void startMjpegServerIproxy() throws IOException {
        int mjpegServerPort = getMjpegServerPort();
        String cmd = String.format(IPROXY, mjpegServerPort, mjpegServerPort, getId());
        log.info("[ios][{}]mjpegServer: {}", getId(), cmd);
        mjpegServerIproxyWatchdog = TerminalUtils.executeAsyncAndGetWatchdog(cmd);
    }

    public void stopMjpegServerIproxy() {
        if (mjpegServerIproxyWatchdog != null) {
            log.info("[ios][{}]mjpegServer iproxy stop", getId());
            mjpegServerIproxyWatchdog.destroyProcess();
        }
    }
}
