#! /bin/bash
sudo usermod -aG docker $(whoami)
docker --version
lscpu | grep Virtualization
sudo apt update
wget -O /tmp/minikube_latest.deb https://storage.googleapis.com/minikube/releases/latest/minikube_latest_amd64.deb
sudo apt install /tmp/minikube_latest.deb
minikube version
sudo snap install kubectl --classic
