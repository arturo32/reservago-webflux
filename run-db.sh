#!/bin/bash
sudo pkill mongo
sudo docker run --name mongo --network="host" -it reservago-webflux-db
