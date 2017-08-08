@echo off
if "%1" == "h" goto begin
mshta vbscript:createobject("wscript.shell").run("""%~nx0"" h",0)(window.close)&&exit
:begin
java -Xmx256m -Xms256m -jar automatically-operate-website-UI-1.1.0.jar