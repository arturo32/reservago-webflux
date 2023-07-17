#!/bin/bash

nlines=$(wc -l < $1)

DIR_NAME=$1
SERVICE_NAME=$1

if [ $SERVICE_NAME = 'config-server' ]; then
	SERVICE_NAME='config'
fi


echo "------------------------------Reseting $DIR_NAME service..."

echo "------------------------------Stopping execution of past containers of the project..."
sudo docker stop reservago-wf-r-${SERVICE_NAME}

echo "------------------------------Removing containers..."
sudo docker rm reservago-wf-r-${SERVICE_NAME}


echo "------------------------------Creating image of the reservago-wf-r-base common library..."
cd ./reservago-base
sudo docker build --tag reservago-wf-r-base:1.0 .


echo "------------------------------Creating image of reservago-wf-r-$SERVICE_NAME..."
cd ../${DIR_NAME}
sudo docker build --tag reservago-wf-r-${SERVICE_NAME}:1.0 .
echo "------------------------------Running container of reservago-wf-r-$SERVICE_NAME..."
sudo docker run -d --name reservago-wf-r-${SERVICE_NAME} --network="host" reservago-wf-r-${SERVICE_NAME}:1.0
