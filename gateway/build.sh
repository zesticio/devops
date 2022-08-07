#!/bin/bash
sudo docker build -t zesticsolutions/oauth.gateway:$1 .
sudo docker push zesticsolutions/oauth.gateway:$1
