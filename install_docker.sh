#!/bin/bash
sudo apt-get update -y
#Older versions of Docker were called docker, docker.io, or docker-engine. If these are installed, uninstall them:
sudo apt-get remove docker docker-engine docker.io containerd runc
sudo apt-get update
sudo apt-get install \
	ca-certificates \
	curl \
	gnupg \
	lsb-release
sudo mkdir -p /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
echo \
	"deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
	$(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

sudo apt-get update
sudo apt-get install docker-ce docker-ce-cli containerd.io docker-compose-plugin
sudo systemctl start docker
sudo systemctl enable docker
sudo systemctl status docker
sudo apt install docker-compose
sudo usermod -aG docker $USER
sudo usermod -aG docker-compose $USER
