#!/bin/bash

PID=$(lsof -t -i :9004)

if [[ ! -z "$PID" && "$PID" != " " ]];
then
        echo 'Dashup application is currently running on pid:' "$PID"
        echo 'Killing process ...'
        kill -9 ${PID}
        echo "Process was killed."
fi

cd ./de.dashup.application
mvn spring-boot:run -Dspring-boot.run.arguments=--database=prod &

echo 'Started dashup application successfully!'