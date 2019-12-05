package com.iqianjin.appperformance.core;

import com.iqianjin.appperformance.util.NetUtils;

public class PortProvider {

    private static final int APPIUM_SERVER_PORT_START = 20000;
    private static final int APPIUM_SERVER_PORT_END = 20999;
    private static int appiumServerPort = APPIUM_SERVER_PORT_START;

    private static final int MINITOUCH_PORT_START = 21000;
    private static final int MINITOUCH_PORT_END = 21999;
    private static int minitouchPort = MINITOUCH_PORT_START;

    private static final int MINICAP_PORT_START = 22000;
    private static final int MINICAP_PORT_END = 22999;
    private static int minicapPort = MINICAP_PORT_START;

    private static final int ADBKIT_PORT_START = 23000;
    private static final int ADBKIT_PORT_END = 23999;
    private static int adbKitPort = ADBKIT_PORT_START;

    private static final int UIAUTOMATOR2_SERVER_PORT_START = 24000;
    private static final int UIAUTOMATOR2_SERVER_PORT_END = 24999;
    private static int uiautomator2ServerPort = UIAUTOMATOR2_SERVER_PORT_START;

    private static final int WDA_LOCAL_PORT_START = 25000;
    private static final int WDA_LOCAL_PORT_END = 25999;
    private static int wdaLocalPort = WDA_LOCAL_PORT_START;

    private static final int WDA_MJPEG_SERVER_PORT_START = 26000;
    private static final int WDA_MJPEG_SERVER_PORT_END = 26999;
    private static int wdaMjpegServerPort = WDA_MJPEG_SERVER_PORT_START;

    private static final int CHROME_DRIVER_PORT_START = 27000;
    private static final int CHROME_DRIVER_PORT_END = 27999;
    private static int chromeDriverPort = CHROME_DRIVER_PORT_START;

    private static final int WEBKIT_DEBUG_PROXY_PORT_START = 28000;
    private static final int WEBKIT_DEBUG_PROXY_PORT_END = 28999;
    private static int webkitDebugProxyPort = WEBKIT_DEBUG_PROXY_PORT_START;

    public static synchronized int getChromeDriverAvailablePort() {
        int availablePort = getAvailablePort(CHROME_DRIVER_PORT_START, CHROME_DRIVER_PORT_END, chromeDriverPort);
        chromeDriverPort = availablePort + 1;
        return availablePort;
    }

    public static synchronized int getWebkitDebugProxyAvalilablePort() {
        int availablePort = getAvailablePort(WEBKIT_DEBUG_PROXY_PORT_START, WEBKIT_DEBUG_PROXY_PORT_END, webkitDebugProxyPort);
        webkitDebugProxyPort = availablePort + 1;
        return availablePort;
    }

    public static synchronized int getAppiumServerAvailablePort() {
        int availablePort = getAvailablePort(APPIUM_SERVER_PORT_START, APPIUM_SERVER_PORT_END, appiumServerPort);
        appiumServerPort = availablePort + 1;
        return availablePort;
    }

    public static synchronized int getMinitouchAvailablePort() {
        int availablePort = getAvailablePort(MINITOUCH_PORT_START, MINITOUCH_PORT_END, minitouchPort);
        minitouchPort = availablePort + 1;
        return availablePort;
    }

    public static synchronized int getMinicapAvailablePort() {
        int availablePort = getAvailablePort(MINICAP_PORT_START, MINICAP_PORT_END, minicapPort);
        minicapPort = availablePort + 1;
        return availablePort;
    }

    public static synchronized int getAdbKitAvailablePort() {
        int availablePort = getAvailablePort(ADBKIT_PORT_START, ADBKIT_PORT_END, adbKitPort);
        adbKitPort = availablePort + 1;
        return availablePort;
    }

    public static synchronized int getUiautomator2ServerAvailablePort() {
        int availablePort = getAvailablePort(UIAUTOMATOR2_SERVER_PORT_START, UIAUTOMATOR2_SERVER_PORT_END, uiautomator2ServerPort);
        uiautomator2ServerPort = availablePort + 1;
        return availablePort;
    }

    public static synchronized int getWdaLocalAvailablePort() {
        int availablePort = getAvailablePort(WDA_LOCAL_PORT_START, WDA_LOCAL_PORT_END, wdaLocalPort);
        wdaLocalPort = availablePort + 1;
        return availablePort;
    }

    public static synchronized int getWdaMjpegServerAvailablePort() {
        int availablePort = getAvailablePort(WDA_MJPEG_SERVER_PORT_START, WDA_MJPEG_SERVER_PORT_END, wdaMjpegServerPort);
        wdaMjpegServerPort = availablePort + 1;
        return availablePort;
    }

    private static int getAvailablePort(int startPort, int endPort, int port) {
        while (true) {
            if (port > endPort || port < startPort) {
                port = startPort;
            }
            if (NetUtils.isPortAvailable(port)) {
                return port;
            } else {
                port++;
            }
        }
    }
}
