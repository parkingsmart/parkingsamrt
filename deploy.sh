export BUILD_ID=dontKillMe

port=8090
project_name=parkingsmart-backend
www_path=/var/prod/${project_name}
jar_path=/var/lib/jenkins/workspace/${project_name}/build/libs
jar_name=parkingsmart-backend-1.0-SNAPSHOT.jar

pid=$(lsof -t -i:${port})
if [ -z "$pid" ]
then
      echo "\$pid is NULL"
else
      echo "\$pid is NOT NULL"
      kill -9 $pid
fi

cp  ${jar_path}/${jar_name} ${www_path}
java -jar --spring.profiles.active=test ${www_path}/${jar_name} &