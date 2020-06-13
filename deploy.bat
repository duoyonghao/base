echo "===========开始部署============="
echo "===========停止服务器==============="

cd D:\tomcat\apache-tomcat-8.0.36\bin && .\shutdown.bat
rm -rf D:\tomcat\apache-tomcat-8.0.36\webapps\base
cp -r target\base D:\tomcat\apache-tomcat-8.0.36\webapps
cd D:\tomcat\apache-tomcat-8.0.36\bin && .\startup.bat
echo "================部署结束============"