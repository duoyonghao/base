@echo off
rem starting deploy
cd %1%\bin && .\shutdown.bat > nul 2>&1
rm -rf %1%\webapps\base
cp -r target\base %1%\webapps
cd %1%\bin && .\startup.bat
rem deploy completed
