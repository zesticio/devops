#Keycloak + Ngnix Self Signed Certificate

## Introduction
Docker compose file triggers three containers to deploy keycloak ssl supported ngnix with mysql database
### Ngnix
### Mariadb
### Keycloak

## Ngnix
Ngnix is a great pice of software that allows you to easily wrap your application inside a reverse-proxy, 
which then handle server related aspects, like SSL and caching, completly transparent to application it.
Additionally, some software is not meant to be available over the internet, since they don't have proper security 
measures in place. Since keycloak is our IAM software, it is good practise that in general to not make internal services
public-facing that don't have to be. 

All of this can be achieved using docker-compose with Ngnix.

CREATE DATABASE keycloak CHARACTER SET utf8 COLLATE utf8_general_ci;
GRANT ALL PRIVILEGES ON *.* TO 'keycloak'@'%' IDENTIFIED BY 'keycloakrw';
