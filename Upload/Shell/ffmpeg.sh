#!/bin/sh
cd $1;
pwd
if [ ! -x ffmpeg.sh ];then
chmod +x ffmpeg.sh;
fi
adb shell am force-stop com.iqianjin.client
adb shell am start -W -n com.iqianjin.client/com.iqianjin.client.asurface.activity.StartActivity
adb shell screenrecord  --time-limit 10   --verbose /sdcard/android_video.mp4;

cd $1;
cd ../screenImage;
if [ ! -d images ];then
mkdir images;
fi
cd images;
pwd;
rm -rf *;
adb pull /sdcard/android_video.mp4 .



