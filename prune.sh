#!/bin/bash
sudo docker images -a
sudo docker system prune
sudo docker system prune -a
sudo docker images -a
sudo systemctl restart docker
sudo docker network prune
