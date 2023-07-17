#!/bin/bash

echo "Stopping execution of past containers of the project..."
sudo docker stop reservago-wf-r-checkout
sudo docker stop reservago-wf-r-admin
sudo docker stop reservago-wf-r-payment
sudo docker stop reservago-wf-r-discovery
sudo docker stop reservago-wf-r-gateway
sudo docker stop reservago-wf-r-config
sudo docker stop reservago-wf-r-db
sudo docker stop reservago-wf-r-redis

echo "Removing containers..."
sudo docker rm reservago-wf-r-checkout
sudo docker rm reservago-wf-r-admin
sudo docker rm reservago-wf-r-payment
sudo docker rm reservago-wf-r-discovery
sudo docker rm reservago-wf-r-gateway
sudo docker rm reservago-wf-r-config
sudo docker rm reservago-wf-r-db
sudo docker rm reservago-wf-r-redis


echo "Creating image of the mongo database..."
sudo docker build -t reservago-wf-r-db:1.0 --target db-img .
echo "Killing any instance of mongo if it's running on this machine..."
sudo pkill mongo
echo "Running container of the mongo database in another terminal..."
sudo x-terminal-emulator -e docker run --name reservago-wf-r-db --network="host" -it reservago-wf-r-db:1.0

echo "Creating image of redis..."
sudo docker build -t reservago-wf-r-redis:1.0 --target redis-img .
echo "Running container of redis in another terminal..."
sudo x-terminal-emulator -e docker run --name reservago-wf-r-redis --network="host" -it reservago-webflux-redis:1.0


echo "Creating image of the reservago-wf-r-base common library..."
cd ./reservago-base
sudo docker build --tag reservago-wf-r-base:1.0 .

echo "Creating image of reservago-wf-r-config..."
cd ../reservago-config-repo
sudo docker build --tag reservago-wf-r-config:1.0 .
echo "Running container of reservago-wf-r-config..."
sudo docker run -d --name reservago-wf-r-config --network host reservago-wf-r-config:1.0

echo "Creating image of reservago-wf-r-discovery..."
cd ../discovery
sudo docker build --tag reservago-wf-r-discovery:1.0 .
echo "Running container of reservago-wf-r-discovery..."
sudo docker run -d --name reservago-wf-r-discovery --network host reservago-wf-r-discovery:1.0

echo "Creating image of reservago-wf-r-gateway..."
cd ../gateway
sudo docker build --tag reservago-wf-r-gateway:1.0 .
echo "Running container of reservago-wf-r-gateway..."
sudo docker run -d --name reservago-wf-r-gateway --network host reservago-wf-r-gateway:1.0

echo "Creating image of reservago-wf-r-admin..."
cd ../admin
sudo docker build --tag reservago-wf-r-admin:1.0 .
echo "Running container of reservago-wf-r-admin..."
sudo docker run -d --name reservago-wf-r-admin --network host reservago-wf-r-admin:1.0

echo "Creating image of reservago-wf-r-checkout..."
cd ../checkout
sudo docker build --tag reservago-wf-r-checkout:1.0 .
echo "Running container of reservago-wf-r-checkout..."
sudo docker run -d --name reservago-wf-r-checkout --network host reservago-wf-r-checkout:1.0

echo "Creating image of reservago-wf-r-payment..."
cd ../payment
sudo docker build --tag reservago-wf-r-payment:1.0 .
echo "Running container of reservago-wf-r-payment..."
sudo docker run -d --name reservago-wf-r-payment --network host reservago-wf-r-payment:1.0

cd ..
echo "Removing intermediary images..."
sudo docker system prune -f
echo "Removing image of reservago-base library..."
sudo docker rmi reservago-wf-r-base:1.0