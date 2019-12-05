package com.iqianjin.appperformance.manager;

import com.iqianjin.appperformance.util.Adbutils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

//获取android启动时长
public class GetStartTime {
    private static Logger logger = LoggerFactory.getLogger(GetStartTime.class);

    //通过adb 命令获取，缺少渲染时长，仅供参考使用
    public static void getAndroidStartTime(int num){
        try {
            FileWriter file = new FileWriter(new File("Upload/startTime.txt"));
            BufferedWriter out = new BufferedWriter(file);
            int sum=0;
            for (int i=1;i<=num;i++){
                String result = getStartTime("com.iqianjin.client");
                sum += Integer.parseInt(result);
                out.write("第"+i+"次启动时间："+result+"\n");
            }
            int average = sum/num;
            out.write("平均值：");
            out.write(String.valueOf(average));
            out.flush();
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static Integer getAndroidStartTime2(int num){
        int sum=0;
        for (int i=1;i<=num;i++){
            String result = getStartTime("com.iqianjin.client");
            sum += Integer.parseInt(result);
        }
        return sum/num;
    }

    public static String getStartTime(String pkgName){
        Adbutils.exec("adb shell am force-stop "+pkgName);
        String procc = Adbutils.exec("adb shell am start -W -n "+pkgName+"/com.iqianjin.client.asurface.activity.StartActivity");
        return procc.substring(procc.indexOf("TotalTime")+11,procc.indexOf("WaitTime"));
    }

    //录屏分帧
    public static void getFFmpeg() {

        String shpath = System.getProperty("user.dir") + "/result/ffmpeg.sh";
        String shdir = System.getProperty("user.dir") + "/result/";
        String imagesPath = System.getProperty("user.dir") + "/screenImage/images/";
        File file = new File(imagesPath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        Adbutils.exec("/bin/sh " + shpath + " " + shdir);
        Adbutils.execAdb("ffmpeg -i "+imagesPath+"android_video.mp4 -r 10  -threads 2  "+imagesPath+"Android-Capture-%05d.png");
    }
    public static void main(String[] args) {
        System.out.println(getAndroidStartTime2(5));
    }

}
