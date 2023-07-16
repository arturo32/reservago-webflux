#!/bin/bash

echo "------------------------------Creating image of the mongo database..."
sudo docker build -t reservago-wf-db:1.0 --target db-img .
echo "------------------------------Killing any instance of mongo if it's running on this machine..."
sudo pkill mongo
echo "------------------------------Running container of the mongo database in another terminal..."
sudo x-terminal-emulator -e docker run --name reservago-wf-db --network="host" -it reservago-wf-db:1.0