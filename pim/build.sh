#!/bin/bash
sudo docker build -t zesticsolutions/pim:$1 .
sudo docker push zesticsolutions/pim:$1
