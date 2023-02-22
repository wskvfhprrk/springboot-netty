#!/bin/bash

echo -e  ======================1.删除运行的服务器======================
ps -aux | grep dtu | grep -v grep| awk '{print $2}' | xargs  kill -9
ps -aux | grep xiaomiqiu | grep -v grep| awk '{print $2}' | xargs  kill -9
#pkill -f dtu
#pkill -f xiaomiqiu

#启动docker
echo -e ======================2.启动docker:为了启动mysql和redis======================
systemctl start docker

cd ~
#删除文件
echo -e ======================3.删除旧的项目文件springboot-netty======================
rm -rf /root/springboot-netty
#git拉取代码
echo -e ======================4.git拉取代码======================
git clone git@github.com:wskvfhprrk/springboot-netty.git
git checkout master
echo -e ======================5.先删除start.sh源文件再拷贝======================r
#先删除源文件再拷贝
cd /root/springboot-netty/
rm -rf /root/start.sh
cp -rf start.sh /root/
chmod +x /root/start.sh
#进入目录中
cd dtu
echo -e ======================6.修改application.yml文件======================
find -name 'application.yml' | xargs perl -pi -e 's|create-drop|update|g'
# 启动时写入数据库中的初始化数据不用写了
find -name 'InitController.java' | xargs perl -pi -e 's|PostConstruct|GetMapping|g'
find -name 'InitController.java' | xargs perl -pi -e 's|javax.annotation.GetMapping|javax.annotation.PostConstruct|g'
#跳过测试建jar包
echo -e ======================7.跳过测试构建jar包======================
mvn clean package -Dmaven.test.skip=true
#重启服务
echo -e ======================8.重启服务======================
cd target
nohup java -jar -Dserver.port=8080 dtu-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &
echo -e ======================9.启动小米球======================

cd /root/linux_xiaomiqu
nohup ./xiaomiqiu -authtoken=46e3527863b34d1d9d65b341019b05f4 -log=xiaomiqiu.log -log-level=info start-all & > /dev/null 2>&1 &