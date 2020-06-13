cd %1%\bin && .\shutdown.bat
rm -rf %1%\webapps\base
cp -r target\base %1%\webapps
cd %1%\bin && .\startup.bat
