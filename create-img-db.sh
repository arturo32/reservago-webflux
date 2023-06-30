#!/bin/bash
sudo docker build -t reservago-webflux-db --target db-img .
sudo docker build -t reservago-webflux-redis --target redis-img .
