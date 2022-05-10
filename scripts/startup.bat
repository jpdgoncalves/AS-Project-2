call %~dp0\vars.bat

if not exist %KAFKA_HOME% (
    echo "Directory %KAFKA_HOME% doesn't exist"
    exit /b
)

start "zookeeper" CMD /k "%KAFKA_BIN_FOLDER%\zookeeper-server-start.bat %KAFKA_CONFIG_FOLDER%\zookeeper.properties"

timeout /t 15

start "kafka" CMD /k "%KAFKA_BIN_FOLDER%\kafka-server-start.bat %KAFKA_CONFIG_FOLDER%\server.properties"

rem further commands can be put bellow
rem this is just a basis for a clean startup of the zookeeper
rem and kafka servers