@echo off

set JAVA_HOME=C:\Program Files\Java\jre1.8.0_66
set PATH=%JAVA_HOME%\bin

rem echo JAVA_HOME : %JAVA_HOME%
rem echo PATH : %PATH%

rem java -version

java -cp WebBrowser.jar com.web.WebBrowser

pause