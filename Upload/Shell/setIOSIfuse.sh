#!/bin/sh
echo "第一个参数$1";
if [ ! -x setIOSIfuse.sh ];then
chmod +x setIOSIfuse.sh;
fi

ifuse --container com.iqianjin.iqianjinplus  $1

