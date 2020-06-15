cls
echo -n "deploy artifacts..."


echo -n "stop tomcat service ..."
cd D:\tomcat\apache-tomcat-8.0.36\bin
Try {
.\shutdown.bat
} Catch {
echo -n "tomcat not start."
}
echo -n "remove old artifacts..."
echo -n "copy new artifacts..."
echo -n "starting tomcat service..."
.\startup.bat
