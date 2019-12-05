package com.iqianjin.appperformance.core.android;

import com.android.ddmlib.AndroidDebugBridge;
import com.iqianjin.appperformance.util.TerminalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;

@Slf4j
public class ADB {

    private static final String ADB_PLATFORM_TOOLS = "platform-tools";

    /**
     * 添加Android设备监听器
     *
     * @param deviceChangeListener
     */
    public static void addDeviceChangeListener(AndroidDebugBridge.IDeviceChangeListener deviceChangeListener) {
        AndroidDebugBridge.init(false);
        AndroidDebugBridge.createBridge(getPath(), false);
        AndroidDebugBridge.addDeviceChangeListener(deviceChangeListener);
    }

    /**
     * 杀掉adb服务
     */
    public static void killServer() throws IOException {
        log.info("[adb]kill-server");
        TerminalUtils.execute("adb kill-server");
    }

    /**
     * 启动adb服务
     */
    public static void startServer() throws IOException {
        log.info("[adb]start-server");
        TerminalUtils.execute("adb start-server");
    }

    /**
     * 获取adb路径
     *
     * @return
     */
    private static String getPath() {
        String androidHome = System.getenv("ANDROID_HOME");
        log.info("[adb]环境变量ANDROID_HOME: {}", androidHome);

        if (StringUtils.isEmpty(androidHome)) {
            throw new RuntimeException("未获取到ANDROID_HOME，请配置ANDOIRD_HOME环境变量");
        }

        String adbPath = androidHome + File.separator + ADB_PLATFORM_TOOLS + File.separator;
        if (TerminalUtils.IS_WINDOWS) {
            adbPath = adbPath + "adb.exe";
        } else {
            adbPath = adbPath + "adb";
        }
        log.info("[adb]adb路径: {}", adbPath);
        return adbPath;
    }
}
