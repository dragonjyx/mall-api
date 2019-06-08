#!/bin/sh  

PROJECT_NAME=mall-web-api
WEB_PATH=/apps/project/webapp/${PROJECT_NAME}
case $1 in
start)

#ps -ef |grep ${PROJECT_NAME}|grep -v grep|awk '{print $2}'|xargs kill -9

sleep 3

echo "start now"

#nohup java -Djava.security.egd=file:/dev/./urandom -Dlogs.path=$WEB_PATH -Dproject.name=${PROJECT_NAME} -jar $WEB_PATH/${PROJECT_NAME}.war  > $WEB_PATH/STDOUT.out 2>&1 &
nohup java -Djava.security.egd=file:/dev/./urandom -Dlogs.path=$WEB_PATH -Dproject.name=${PROJECT_NAME} -jar $WEB_PATH/${PROJECT_NAME}.war >/dev/null 2>&1 &

echo "${PROJECT_NAME} start success"

sleep 1


;;
stop)

ps -ef |grep ${PROJECT_NAME}|grep -v grep|awk '{print $2}'|xargs kill -9

echo "${PROJECT_NAME} stop success"

;;
*)
echo "Uage: start|stop"
;;
esac
 
exit 0





