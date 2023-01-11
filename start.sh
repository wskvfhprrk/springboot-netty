#删除服务器
ps -aux | grep studay | grep -v grep| awk '{print $2}' |xargs  kill -9
cd ~
#删除文件
rm -rf springboot-netty
#git拉取代码
git clone git@github.com:wskvfhprrk/springboot-netty.git
cd /root/springboot-netty/
cp -rf start.sh /root/
#进入目录中
cd studay
#跳过测试建jar包
mvn clean package -Dmaven.test.skip=true

#重启服务
cd /root/springboot-netty/studay/target
nohup java -jar -Dserver.port=8080 studay-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &

#nohup java -jar -Dserver.port=8080 /root/springboot-netty/studay/target/studay-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &
