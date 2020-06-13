echo "===========开始部署============="
echo "===========停止服务器==============="

cd %1%\bin && .\shutdown.bat
rm -rf %1%\webapps\base
cp -r target\base %1%\webapps
cd %1%\bin && .\startup.bat
echo "================部署结束============"