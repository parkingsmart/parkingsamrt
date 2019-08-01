export BUILD_ID=dontKillMe

port=8090

pid=$(lsof -t -i:${port})
if [ -z "$pid" ]
then
      echo "\$pid is NULL"
else
      echo "\$pid is NOT NULL"
      kill -9 $pid
fi