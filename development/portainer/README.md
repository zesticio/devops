# Portainer

## Create a docker network
docker network create --driver=bridge --subnet=172.0.1.0/24 --ip-range=172.0.1.0/24 --gateway=172.0.1.1 portainer

You can list what networks you have in your docker environment with this command: `docker network ls`

