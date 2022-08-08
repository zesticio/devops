set arg1=%1
docker build -t zesticsolutions/firehose:%arg1% .
docker push zesticsolutions/firehose:%arg1%
