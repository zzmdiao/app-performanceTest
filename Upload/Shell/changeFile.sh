#!/bin/sh
echo "第一个参数$1";
cd $1;
if [ ! -x changeFile.sh ];then
chmod +x changeFile.sh;
fi
#rm -rf ../finalResult/*
#for x in `ls *.hprof`;do a=`echo $x|awk -F '_' '{print "2-"$1"_"$2}'`; hprof-conv -z $x $a; done
for x in `ls *.hprof`;do a=`echo $x|awk -F '.' '{print $1"-2""."$2}'`; hprof-conv -z $x $a; done
# mv *-2.hprof ../finalResult/
# rm -rf *.hprof
# mv *.txt ../finalResult/
