#!/bin/bash
#sudo docker stats

#You might get an error while creating docker containers for authy related applications
#docker failed to create endpoint network bridge
#restart the server it should work
#Arch Linux, rebooting fix it, It might be related to the recent system update.

sudo docker network create --driver=bridge --subnet=172.27.0.0/16 --ip-range=172.27.0.0/16 authy.network
sudo docker network ls
