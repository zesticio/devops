set arg1=%1
docker build -t zesticsolutions/oauth.gateway:%arg1% .
docker push zesticsolutions/oauth.gateway:%arg1%
