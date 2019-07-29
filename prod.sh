port=8090

pid=$(lsof -t -i:${port})
if [ -z "$pid" ]
then
      echo "\$pid is NULL"
else
      echo "\$pid is NOT NULL"
      kill -9 $pid
fi

nohup java -jar -Dspring.profiles.active=prod /usr/local/webserver/nginx/html/parkingsmart-backend-1.0-SNAPSHOT.jar > log.txt 2>&1 &