#!/bin/bash
sudo docker build -t zesticsolutions/firehose:$1 .
sudo docker push zesticsolutions/firehose:$1
