#!/bin/bash
sudo docker build -t zesticsolutions/oauth.discovery:$1 .
sudo docker push zesticsolutions/oauth.discovery:$1
