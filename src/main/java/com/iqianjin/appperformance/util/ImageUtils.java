package com.iqianjin.appperformance.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;


public class ImageUtils {
    private static ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("image-pool-%d").build();

    private static ExecutorService pool = new ThreadPoolExecutor(5, 10,
            1L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(5), threadFactory, new ThreadPoolExecutor.AbortPolicy());

    public static String platformName = "";

    /**
     * 截屏，会在element元素上添加矩形红框
     * 注意：此操作要放在点击事件之前，不然可能会抛出StaleObjectException异常（因为页面刷新了，element元素可能会失效，需要重新定位）
     *
     * @param element
     * @param appiumDriver
     */
    public static void drawScreen(WebElement element, AppiumDriver appiumDriver) {
//        TODO:截图，执行效率变慢
//        如果ios截图，注掉下方代码
        if ("ios".equalsIgnoreCase(platformName)){
            return;
        }
        File srcFile = appiumDriver.getScreenshotAs(OutputType.FILE);
        Point point = element.getLocation();
        int x =point.x;
        int y = point.y;
        Dimension dimension = element.getSize();
        int width = dimension.width;
        int height = dimension.height;
        pool.execute(() -> {
            BufferedImage image;
            FileOutputStream out;//输出图片的地址
            try {
                image = ImageIO.read(srcFile);
                Graphics2D g = image.createGraphics();
                g.setColor(Color.RED);//画笔颜色
                g.setStroke(new BasicStroke(5.0f)); //线粗
                //ios屏幕大小默认是pt，需要转换成px
                if ("android".equalsIgnoreCase(platformName)) {
                    g.drawRect(x, y, width, height);//矩形框(原点x坐标，原点y坐标，矩形的长，矩形的宽)
                } else {
                    g.drawRect(x * 3, y * 3, width * 3, height * 3);//矩形框(原点x坐标，原点y坐标，矩形的长，矩形的宽)
                }
                File dirPath = new File(getPath());
                if (!dirPath.getParentFile().exists()) {
                    dirPath.getParentFile().mkdirs();
                }
                out = new FileOutputStream(getPath());
                ImageIO.write(image, "png", out);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public static void shutPool() {
        pool.shutdown();
    }

    /**
     * 截屏
     *
     * @param appiumDriver
     */
    public static void drawScreen(AppiumDriver appiumDriver) {
        if ("ios".equalsIgnoreCase(platformName)){
            return;
        }
        File srcFile = appiumDriver.getScreenshotAs(OutputType.FILE);
        String path = getPath();
        File dirPath = new File(getPath());
        if (!dirPath.getParentFile().exists()) {
            dirPath.getParentFile().mkdirs();
        }
        try {
            FileUtils.copyFile(srcFile, new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPath() {
        String path;
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if ("android".equalsIgnoreCase(platformName)) {
            path = System.getProperty("user.dir") + "/screenImage/" + "android" + format.format(date) +
                    File.separator + sdf.format(date) + ".png";
        } else {
            path = System.getProperty("user.dir") + "/screenImage/" + "ios" + format.format(date) +
                    File.separator + sdf.format(date) + ".png";
        }
        return path;
    }
}
