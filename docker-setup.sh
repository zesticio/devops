#! /bin/bash
echo "Update Ubuntu"
sudo apt-get update -y
echo "Upgrade Ubuntu"
sudo apt upgrade
echo "Install the required dependencies"
sudo apt install ca-certificates curl gnupg lsb-release
sudo apt-get remove docker docker-engine docker.io containerd runc
echo "To install the GPG key of the official Docker CE repository, create a new directory"
sudo mkdir -p /etc/apt/keyrings
echo "Download the GPG key file of the official Docker CE repository"
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list >/dev/null
sudo apt-get update

echo "Install Docker CE from the official Docker CE package repository"
sudo apt-get install docker-ce docker-ce-cli containerd.io docker-compose-plugin
sudo apt install docker-compose
sudo usermod -aG docker $(whoami)

echo "Starting Docker"
sudo usermod -aG docker $USER
newgrp docker
sudo systemctl start docker
sudo systemctl enable docker
sudo systemctl status docker