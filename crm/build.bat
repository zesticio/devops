set arg1=%1
docker build -t zesticsolutions/oauth.discovery:%arg1% .
docker push zesticsolutions/oauth.discovery:%arg1%
