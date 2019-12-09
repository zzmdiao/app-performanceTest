#!/bin/sh

if [ ! -x moveFile.sh ];then
chmod +x moveFile.sh;
fi

cd $1
echo `pwd`
mv $1/*.txt $2
