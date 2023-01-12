#!/bin/bash

#echo -e "\033[3${i};4${j}m文字色值 ${i}, 背景色值 ${j}\033[0m"
#cho -e "\033[3${0};4${7}m文字色值 ${0}, 背景色值 ${7}\033[0m"
#删除服务器
echo -e  ======================1.删除运行的服务器======================
ps -aux | grep studay | grep -v grep| awk '{print $2}' |xargs  kill -9
#启动docker
echo -e ======================2.启动docker======================
systemctl start docker
cd ~
#删除文件
echo -e ======================3.删除文件springboot-netty======================
rm -rf springboot-netty
#git拉取代码
echo -e ======================4.git拉取代码======================
git clone git@github.com:wskvfhprrk/springboot-netty.git
echo -e ======================5.先删除源文件再拷贝======================
cd /root/springboot-netty/
#先删除源文件再拷贝
rm -rf /root/start.sh
cp -rf start.sh /root/
#进入目录中
cd studay
#跳过测试建jar包
echo -e ======================6.跳过测试构建jar包======================
mvn clean package -Dmaven.test.skip=true
#重启服务
echo -e ======================7.重启服务======================
cd /root/springboot-netty/studay/target
nohup java -jar -Dserver.port=8080 studay-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &
echo -e
echo -e
echo -e =================请自行打开小米球操作======================
echo -e
echo -e 开新窗口:输入screen -S xiaomiqiu
echo -e
echo -e 会弹出新的窗口,在里面输入启动指令
echo -e
echo -e 启动指令:    ./xiaomiqiu -authtoken=bAe854993e6444e3925b24c7edcdd72A -log=xiaomiqiu.log -log-level=info start-all
echo -e
echo -e
echo -e
sleep 10
echo -e ======================8.自动打开日志======================
tail -f /home/logs/dtu/logback_info.log
#接下来在服务器上安装screen
#yum install screen
#cd /root/linux_xiaomiqu
#screen -S xiaomiqiu
#启动小米球
#./xiaomiqiu -authtoken=bAe854993e6444e3925b24c7edcdd72A -log=xiaomiqiu.log -log-level=info start-all
#如何关闭？
#ssh上主机
#screen -r xiaomiqiu
#此时就会出现你刚才的端口