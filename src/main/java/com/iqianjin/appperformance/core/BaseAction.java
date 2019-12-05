package com.iqianjin.appperformance.core;

import com.iqianjin.appperformance.App;
import com.iqianjin.appperformance.core.android.AndroidUtil;
import com.iqianjin.appperformance.db.model.Param;
import com.iqianjin.appperformance.manager.ElementManager;
import com.iqianjin.appperformance.util.ImageUtils;
import com.iqianjin.appperformance.util.TerminalUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class BaseAction {
    private final ThreadLocal<AppiumDriver> appiumDriver = new ThreadLocal();
    private final ThreadLocal<Integer> platform = new ThreadLocal();

    public ThreadLocal<AppiumDriver> getAppiumDriver() {
        return appiumDriver;
    }

    public void setAppiumDriver(AppiumDriver driver) {
        appiumDriver.set(driver);
    }

    public ThreadLocal<Integer> getPlatform() {
        return platform;
    }

    public void setPlatform(Integer plat) {
        platform.set(plat);
    }

    public void removeAppiumDriver() {
        appiumDriver.remove();
    }

    public void removePlatform() {
        platform.remove();
    }

    List<String> dialogsWebview = Stream.of(".dialog-invest-close", ".close")
            .collect(Collectors.toList());

    //ios左上角返回元素
    protected List<String> iosBack = Stream.of("public back",
            "ticket back n",
            "public back white").
            collect(Collectors.toList());

    /**
     * 查找元素
     *
     * @param locator
     * @return
     */
    public WebElement findElement(String locator) {
        List<Param> location;
        WebElement element = null;
        location = App.getBean(ElementManager.class).getElementList(locator, platform.get());
        for (Param temp : location) {
            String findBy = temp.getParamBy();
            String value = temp.getParamValue();
            By by = getBy(findBy, value);
            try {
                element = appiumDriver.get().findElement(by);
            } catch (Exception e) {
                log.error("findElement无法查询到该元素:{}", locator);
            }

        }

        return element;
    }

    public WebElement waitForElementVisible(String locator, Integer maxWaitTimeInSeconds) {
        List<Param> location;
        WebElement element = null;
        location = App.getBean(ElementManager.class).getElementList(locator, platform.get());
        if (location == null) {
            return null;
        }
        for (Param temp : location) {
            String findBy = temp.getParamBy();
            String value = temp.getParamValue();
            try {
                element = new WebDriverWait(appiumDriver.get(), maxWaitTimeInSeconds, 500)
                        .until(ExpectedConditions.presenceOfElementLocated(getBy(findBy, value)));
                if (element != null) {
                    return element;
                }
            } catch (Exception e) {
                log.error("waitForElementVisible无法查询到该元素:{}", locator);
            }

        }
        return element;

    }

    /**
     * 普通点击
     *
     * @param locator
     * @return
     */
    public WebElement click(String locator) {
        WebElement element = waitForElementVisible(locator, 2);
        try {
            if (element != null) {
                element.click();
            }
        } catch (Exception e) {
            log.error("点击事件报错:{}", locator);
            e.printStackTrace();
        }

        return element;
    }

    /**
     * 点击元素
     *
     * @param element
     * @return
     */
    public WebElement click(WebElement element) {
        try {
            element.click();
        } catch (Exception e) {
            log.error("element点击事件报错:{}", element);
            e.printStackTrace();
        }

        return element;
    }

    /**
     * 输入
     *
     * @param locator 输入框定位符
     * @param content 输入内容
     * @return
     */
    public WebElement sendKeys(String locator, String content) {

        WebElement element = findElement(locator);
        if (element != null) {
            element.clear();
            element.sendKeys(content);
        }
        return element;
    }

    /**
     * 返回
     */
    public void goBack() {

        if (MobileDevice.ANDROID == (platform.get())) {
            appiumDriver.get().navigate().back();
        } else {
            WebElement element = null;
            for (String str : iosBack) {
                try {
                    element = appiumDriver.get().findElementById(str);
                } catch (Exception e) {
                    log.error("ios返回点击错误:{}", str);
                }
                if (element != null) {
                    element.click();
                    log.info("ios点击返回元素:{}", str);
                    break;
                }
            }
        }
    }

    /**
     * 上下滑动到指定元素
     *
     * @param to
     * @param from
     * @param locatorName
     */
    public WebElement swipUpWaitElement(double to, double from, String locatorName) {
        WebElement element;
        log.info("滑动到指定元素:{}", locatorName);
        int num = 0;
        while (true) {
            swipeUpOrDown(to, from);
            element = findElement(locatorName);
            if (element != null) {
                return element;
            }
            num++;
            if (num > 20) {
                log.error("滑动到指定元素:{},超过20次", locatorName);
                return null;
            }
        }

    }

    /**
     * 上滑和下滑，根据系数滑动
     *
     * @param to   滑动起始位系数
     * @param from 滑动终点系数
     */
    public void swipeUpOrDown(double to, double from) {
        int height = appiumDriver.get().manage().window().getSize().height;
        int width = appiumDriver.get().manage().window().getSize().width;
        TouchAction action = new TouchAction(appiumDriver.get());
        try {
            action.longPress(PointOption.point(width / 2, (int) (height * to)))
                    .moveTo(PointOption.point(width / 2, (int) (height * from)))
                    .release().perform();
        } catch (Exception e) {
            log.error("向上or向下滑动失败");
            e.printStackTrace();
        }

    }

    /**
     * 左滑，右滑
     */
    public void swipeLeftOrRight(double to, double from) {
        log.info("开始左右滑动");
        int width = appiumDriver.get().manage().window().getSize().width;
        int height = appiumDriver.get().manage().window().getSize().height;
        TouchAction action = new TouchAction(appiumDriver.get());
        try {
            action.longPress(PointOption.point((int) (width * to), height / 2)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                    .moveTo(PointOption.point((int) (width * from), height / 2)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                    .release().perform();
        } catch (Exception e) {
            log.error("向左or向右滑动失败");
            e.printStackTrace();
        }

    }

    /**
     * 上下滑动指定次数
     *
     * @param to
     * @param from
     * @param num
     */
    public void swipeToNum(double to, double from, int num) {
        swipeUpOrDown(to, from);
        //todo 优化：ios执行比较慢
        String pageSource = appiumDriver.get().getPageSource();

        if (pageSource.contains("去别处看看吧") || pageSource.contains("暂无预约记录") ||
                pageSource.contains("暂无流水记录") || pageSource.contains("暂无资金流水")
                || pageSource.contains("没有更多")) {
            return;
        }

        log.info("上下滑动次数:{}", num);
        for (int i = 1; i < num; i++) {
            swipeUpOrDown(to, from);
        }
    }

    /**
     * 长按某一坐标
     *
     * @param x
     * @param y
     */
    public void longPreePostion(int x, int y) {
        log.info("长按坐标x{}, y{}", x, y);
        TouchAction action = new TouchAction(appiumDriver.get());
        action.longPress(LongPressOptions.longPressOptions()
                .withPosition(PointOption.point(x, y))
                .withDuration(Duration.ofSeconds(2)));
        action.release().perform();
    }

    /**
     * 当前页面是否包含某一元素
     *
     * @param locatorName 元素定位
     * @param content     元素的内容
     * @return
     */
    public boolean isCanFindElement(String locatorName, String... content) {
        WebElement element = findElement(locatorName);
        return isContent(element, content);
    }

    public boolean isContent(WebElement element, String... content) {
        if (null != element && content.length == 0) {
            return true;
        }
        if (null != element && content[0].equals(element.getText())) {
            return true;
        }
        return false;
    }

    public void clearApkData(String packageName) throws Exception {
        Assert.hasText(packageName, "包名不能为空");

        AndroidUtil.clearApkData(MobileDeviceHolder.getIDeviceByAppiumDriver(appiumDriver.get()), packageName);
    }


    public void restartApk(String packageName, String launchActivity) {
        Assert.hasText(packageName, "包名不能为空");
        Assert.hasText(launchActivity, "启动Activity不能为空");
        try {
            AndroidUtil.restartApk(MobileDeviceHolder.getIDeviceByAppiumDriver(appiumDriver.get()), packageName, launchActivity);
        } catch (Exception e) {
            log.error("启动app出错:{}  :{}", packageName, launchActivity);
            e.printStackTrace();
        }
    }

    public String getPageSource() {
        return appiumDriver.get().getPageSource();
    }

    /**
     * 判断是否是webview
     *
     * @return
     */
    public boolean isWebview() {
        log.info("判断是否有webview页面");
        TerminalUtils.sleep(2);
        Set<String> contextHandles = appiumDriver.get().getContextHandles();
        log.info("当前contextHandle数量:{},内容：{}", contextHandles.size(), contextHandles);
        if (contextHandles.size() == 1) {
            return false;
        } else {
            contextHandles.forEach((handle) -> {
                if (handle.contains("WEBVIEW")) {
                    log.info("切换到webView:{}", handle);
                    try {
                        appiumDriver.get().context(handle);
                    } catch (Exception e) {
                        log.error("当前handle是:{}", handle);
                        e.printStackTrace();
                    }
                }
            });
            return true;
        }
    }

    /**
     * 点击webview元素
     */
    public void clickWebview() {
        for (String temp : dialogsWebview) {
            try {
                WebElement element = appiumDriver.get().findElementByCssSelector(temp);
                if (element != null) {
                    element.click();
                }
                log.info("点击webview元素:{}", temp);
            } catch (Exception e) {
                log.error("找不到webview元素:{}", temp, e);
            }
        }
        log.info("切换到native");
        appiumDriver.get().context("NATIVE_APP");
    }

    private By getBy(String findBy, String value) {
        By by;
        switch (findBy) {
            case "id":
                by = MobileBy.id(value);
                break;
            case "AccessibilityId":
                by = MobileBy.AccessibilityId(value);
                break;
            case "xpath":
                by = MobileBy.xpath(value);
                break;
            case "AndroidUIAutomator":
                // http://appium.io/docs/en/writing-running-appium/android/uiautomator-uiselector/
                value = value.replaceAll("'", "\"");
                by = MobileBy.AndroidUIAutomator(value);
                break;
            case "iOSClassChain":
                by = MobileBy.iOSClassChain(value);
                break;
            case "pre":
                // http://appium.io/docs/en/writing-running-appium/ios/ios-predicate/
                by = MobileBy.iOSNsPredicateString(value);
                break;
            case "image":
                by = MobileBy.image(value);
                break;
            default:
                throw new RuntimeException("暂不支持: " + findBy);
        }
        return by;
    }
}
