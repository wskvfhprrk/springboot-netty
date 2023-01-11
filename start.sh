#删除服务器
@echo OFF
color 0a
Title 小米球一键启动工具 by:刺球
Mode con cols=109 lines=30
:START
ECHO.
Echo                  ==========================================================================
ECHO.
Echo                                            netty服务器启动
ECHO.
Echo                  ==========================================================================
Echo.
echo.
echo.  删除服务器
ps -aux | grep studay | grep -v grep| awk '{print $2}' |xargs  kill -9
#启动docker
echo.  启动docker
systemctl start docker
cd ~
#删除文件
echo. 删除旧文件
rm -rf springboot-netty
#git拉取代码
git clone git@github.com:wskvfhprrk/springboot-netty.git
cd /root/springboot-netty/
#先删除源文件再拷贝
rm -rf /root/start.sh
cp -rf start.sh /root/
#进入目录中
cd studay
#跳过测试建jar包
mvn clean package -Dmaven.test.skip=true

#重启服务
cd /root/springboot-netty/studay/target
nohup java -jar -Dserver.port=8080 studay-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &
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