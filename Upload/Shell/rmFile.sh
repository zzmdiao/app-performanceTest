#!/bin/sh

if [ ! -x rmFile.sh ];then
chmod +x rmFile.sh;
fi
echo "第一个参数是$1"
cd $1
echo `pwd`
rm -rf *.txt
