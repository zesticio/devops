#!/bin/bash
sudo docker network create --driver=bridge --subnet=172.27.0.0/16 --ip-range=172.27.0.0/16 zestic
sudo docker network ls
