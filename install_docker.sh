#!/bin/bash
sudo apt-get update -y
sudo apt-get remove docker docker-engine docker.io
sudo apt install docker.io
sudo systemctl start docker
sudo systemctl enable docker
sudo systemctl status docker
sudo apt install docker-compose

sudo usermod -aG docker $USER
sudo usermod -aG docker-compose $USER
