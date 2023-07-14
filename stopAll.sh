#!/bin/bash
sudo docker container start $(sudo docker container ls -a -q)