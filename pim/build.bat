set arg1=%1
docker build -t zesticsolutions/pim:%arg1% .
docker push zesticsolutions/pim:%arg1%
