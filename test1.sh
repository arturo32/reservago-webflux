#!/bin/bash

echo "Stopping execution of past containers of the project..."
sudo docker stop reservago-wf-admin
sudo docker stop reservago-wf-db
sudo docker stop reservago-wf-redis

echo "Removing containers..."
sudo docker rm reservago-wf-admin
sudo docker rm reservago-wf-db
sudo docker rm reservago-wf-redis


echo "Creating image of the mongo database..."
sudo docker build -t reservago-wf-db:1.0 --target db-img .
echo "Killing any instance of mongo if it's running on this machine..."
sudo pkill mongo
echo "Running container of the mongo database in another terminal..."
sudo x-terminal-emulator -e docker run --name reservago-wf-db --network="host" -it reservago-wf-db:1.0

echo "Creating image of redis..."
sudo docker build -t reservago-wf-redis:1.0 --target redis-img .
echo "Running container of redis in another terminal..."
sudo x-terminal-emulator -e docker run --name reservago-wf-redis --network="host" -it reservago-wf-redis:1.0


echo "Creating image of the reservago-wf-base common library..."
cd ./reservago-base
sudo docker build --tag reservago-wf-base:1.0 .

echo "Creating image of reservago-wf-admin..."
cd ../admin
sudo docker build --tag reservago-wf-admin:1.0 .
echo "Running container of reservago-wf-admin..."
sudo docker run -d --name reservago-wf-admin --network host reservago-wf-admin:1.0


cd ..
echo "Removing intermediary images..."
sudo docker system prune -f
echo "Removing image of reservago-base library..."
sudo docker rmi reservago-wf-base:1.0