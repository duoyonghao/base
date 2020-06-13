echo "===========开始部署============="
echo "===========停止服务器==============="

try {
    cd D:\tomcat\apache-tomcat-8.0.36\bin && .\shutdown.bat
}
catch {
}

echo "================部署结束============"