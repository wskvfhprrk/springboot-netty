cd ~
#删除文件
rm -rf springboot-netty
#git拉取代码
git clone git@github.com:wskvfhprrk/springboot-netty.git
#进入目录中
cd springboot-netty/studay/
#跳过测试建jar包
mvn clean package -Dmaven.test.skip=true