#!/bin/bash
sudo docker build -t zesticsolutions/discovery:$1 .
sudo docker push zesticsolutions/discovery:$1
