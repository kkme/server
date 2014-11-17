@java -Xms500m -Xmx1200m -cp "h2-1.2.140.jar" org.h2.tools.Console %*
@if errorlevel 1 pause