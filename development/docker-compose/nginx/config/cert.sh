#!/bin/bash
echo "We can create a self-signed key and certificate pair with OpenSSL in a single command"
sudo openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout nginx.key -out nginx.crt
sudo openssl dhparam -out nginx.pem 2048
