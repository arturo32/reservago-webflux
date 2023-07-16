#!/bin/bash

nlines=$(wc -l < $1)

DIR_NAME=$1
SERVICE_NAME=$1

if [ $SERVICE_NAME = 'config-server' ]; then
	SERVICE_NAME='config'
fi


echo "------------------------------Reseting $DIR_NAME service..."

echo "------------------------------Stopping execution of past containers of the project..."
sudo docker stop reservago-wf-${SERVICE_NAME}

echo "------------------------------Removing containers..."
sudo docker rm reservago-wf-${SERVICE_NAME}


echo "------------------------------Creating image of the reservago-wf-base common library..."
cd ./reservago-base
sudo docker build --tag reservago-wf-base:1.0 .


echo "------------------------------Creating image of reservago-wf-$SERVICE_NAME..."
cd ../${DIR_NAME}
sudo docker build --tag reservago-wf-${SERVICE_NAME}:1.0 .
echo "------------------------------Running container of reservago-wf-$SERVICE_NAME..."
sudo docker run -d --name reservago-wf-${SERVICE_NAME} --network="host" reservago-wf-${SERVICE_NAME}:1.0
