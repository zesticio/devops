#!/bin/sh
sudo apt-get update
sudo apt-get install default-jdk -y
sudo groupadd keycloak
sudo useradd -r -g keycloak -d /opt/keycloak -s /sbin/nologin keycloak
sudo chown -R keycloak:keycloak /opt/keycloak
sudo chmod o+x /opt/keycloak/bin/
sudo mkdir -p /etc/keycloak
sudo cp /opt/keycloak/docs/contrib/scripts/systemd/wildfly.conf /etc/keycloak/keycloak.conf
sudo cp launch.sh /opt/keycloak/bin/
sudo chown keycloak: /opt/keycloak/bin/launch.sh
sudo ufw allow 8080

echo "Please update Keycloak installation path in launch.sh"
sudo cp keycloak.service /etc/systemd/system/keycloak.service
