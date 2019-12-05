package com.iqianjin.appperformance.manager;

import com.iqianjin.appperformance.core.BaseAction;
import com.iqianjin.appperformance.core.MobileDevice;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
@Slf4j
public class LoginManager {
    private String myTab = "我的tab";
    public String tv_start = "开机广告";
    private String otherLogin = "其他登录";
    private String userName = "用户名";
    private String passwdStatus = "密码";
    private String loginSubmit = "登录按钮";
    private String gestureForget = "忘记手势密码";
    private String setLock = "请绘制解锁图案";
    private String loginAgain = "重新登录";
    private String setUP = "设置按钮";
    private String loginOut = "退出登录";
    private String confirmButton = "确认按钮";

    @Autowired
    private BaseAction baseAction;

    public void login(String user, String pass) {

        baseAction.click(tv_start);
        log.info("开始进入注册/登录");
        if (isLogin()) {
            if (baseAction.getPageSource().contains(user)) {
                log.info("当前用户是同一个，尝试解锁");
                setOneUnlock();
                if (baseAction.getPageSource().contains("密码输入错误")) {
                    logOut(user);
                    loginAgain(user, pass);
                }
            } else {
                logOut(user);
                loginAgain(user, pass);
            }
        } else {
            baseAction.click(otherLogin);
            loginAgain(user, pass);
        }

    }

    /**
     * 判断是否已登录
     */
    public boolean isLogin() {

        boolean flag = false;
        if (baseAction.isWebview()) {
            if (MobileDevice.ANDROID == baseAction.getPlatform().get()) {
                baseAction.goBack();
            } else {
                baseAction.clickWebview();
            }
        }

        WebElement myTabElement = baseAction.findElement(myTab);
        if (null != myTabElement) {
            baseAction.click(myTabElement);
            if (null != baseAction.findElement(otherLogin)) {
                log.info("点击我的，当前是否是登录状态:{}", flag);
                return flag;
            }
        }

        if (null != baseAction.findElement(gestureForget)
                || null != baseAction.findElement(setLock)) {
            flag = true;
        }
        log.info("当前是否是登录状态:{}", flag);
        return flag;
    }

    public void loginAgain(String user, String pass) {
        for (int i = 0; i < 3; i++) {
            if (loginFail()) {
                baseAction.sendKeys(userName, user);
                baseAction.sendKeys(passwdStatus, pass);
                baseAction.click(loginSubmit);
            } else {
                log.info("登录完成");
                break;
            }
        }

        setUnlock();
    }

    /**
     * 退出登录
     *
     * @param
     */
    public void logOut(String user) {
        String pagesource = baseAction.getPageSource();
        if (pagesource.contains("忘记手势密码")) {
            log.info("当前页面包含《忘记手势密码》元素");
            baseAction.click(gestureForget);
            baseAction.click(loginAgain);
            if (MobileDevice.ANDROID == baseAction.getPlatform().get()) {
                baseAction.click(otherLogin);
            }
        }
        if (pagesource.contains("请绘制解锁图案")) {
            setUnlock();
            baseAction.click(myTab);
            if (baseAction.getPageSource().contains(user)) {
                return;
            }
            baseAction.click(setUP);
//            swipUpWaitElement(0.5, 0.1, loginOut);
            baseAction.click(loginOut);
            baseAction.click(confirmButton);
            baseAction.click(myTab);
            baseAction.click(otherLogin);
        }
    }

    public void setUnlock() {
        if (baseAction.getPageSource().contains("设置")) {
            return;
        }
        if (MobileDevice.ANDROID == baseAction.getPlatform().get()) {
            getViewUnlockAndroid("gestureSetLockView");
            getViewUnlockAndroid("gestureSetLockView");
        } else {
            getViewUnlockIos();
            getViewUnlockIos();
        }
    }

    public void setOneUnlock() {
        if (baseAction.getPageSource().contains("设置")) {
            return;
        }
        if (MobileDevice.ANDROID == baseAction.getPlatform().get()) {
            getViewUnlockAndroid("gestureLockPatternView");
        } else {
            getViewUnlockIos();
        }
    }

    public Boolean loginFail() {
        if (baseAction.isCanFindElement(myTab)){
            baseAction.click(myTab);
            return false;
        }
        if (null != baseAction.findElement(setUP)
                || null != baseAction.findElement(setLock)) {
            return false;
        } else {
            return true;
        }
    }

    //根据view插件设置九宫格解锁
    public void getViewUnlockAndroid(String ElementView) {
        log.info("设置android解锁图案L");
        WebElement webElement = baseAction.getAppiumDriver().get().findElement(By.id(ElementView));
        //获取控件起始坐标的x、y
        int bX = webElement.getLocation().getX();
        int bY = webElement.getLocation().getY();
        //获取控件的高度、宽度
        int width = webElement.getSize().getWidth();
        int height = webElement.getSize().getHeight();
        //宽度、高度之间的间隔
        int x = bX + width / 6;
        int y = bY + height / 6;
        int xStep = width / 3;
        int yStep = height / 3;
        int xxStep = 2 * xStep;
        int yyStep = 2 * yStep;
        TouchAction ta = new TouchAction(baseAction.getAppiumDriver().get());
        Duration duration = Duration.ofMillis(500);
        ta.longPress(PointOption.point(bX, bY)).waitAction(WaitOptions.waitOptions(duration))
                .moveTo(PointOption.point(x, y)).waitAction(WaitOptions.waitOptions(duration))
                .moveTo(PointOption.point(x, y + yStep)).waitAction(WaitOptions.waitOptions(duration))
                .moveTo(PointOption.point(x, y + yyStep)).waitAction(WaitOptions.waitOptions(duration))
                .moveTo(PointOption.point(x + xStep, y + yyStep)).waitAction(WaitOptions.waitOptions(duration))
                .moveTo(PointOption.point(x + xxStep, y + yyStep)).waitAction(WaitOptions.waitOptions(duration))
                .release().perform();
    }

    public void getViewUnlockIos() {
        log.info("设置ios解锁图案L");
        List<WebElement> elements = baseAction.getAppiumDriver().get().findElements(By.xpath("//XCUIElementTypeButton"));
        Point firstElement = elements.get(0).getLocation();
        Point thridElement = elements.get(3).getLocation();
        Point sixElement = elements.get(6).getLocation();
        Point sevenElement = elements.get(7).getLocation();
        Point eightElement = elements.get(8).getLocation();
        //获取控件起始坐标的x、y
        int bX = firstElement.getX();
        int bY = firstElement.getY();
        // 第二个点的坐标
        int twoX = thridElement.getX();
        int twoY = thridElement.getY();
        // 第三个点的坐标
        int threeX = sixElement.getX();
        int threeY = sixElement.getY();
        // 第四个点的坐标
        int fourX = sevenElement.getX();
        int fourY = sevenElement.getY();
        // 第五个点的坐标
        int fiveX = eightElement.getX();
        int fiveY = eightElement.getY();
        TouchAction ta = new TouchAction(baseAction.getAppiumDriver().get());
        Duration duration = Duration.ofMillis(500);
        ta.longPress(PointOption.point(bX, bY)).waitAction(WaitOptions.waitOptions(duration))
                .moveTo(PointOption.point(twoX, twoY)).waitAction(WaitOptions.waitOptions(duration))
                .moveTo(PointOption.point(threeX, threeY)).waitAction(WaitOptions.waitOptions(duration))
                .moveTo(PointOption.point(fourX, fourY)).waitAction(WaitOptions.waitOptions(duration))
                .moveTo(PointOption.point(fiveX, fiveY)).waitAction(WaitOptions.waitOptions(duration))
                .release().perform();

    }
}
