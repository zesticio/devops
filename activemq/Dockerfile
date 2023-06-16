# Using jdk as base image
FROM openjdk:8-jdk-alpine
# Copy the whole directory of activemq into the image
COPY apache-activemq-5.15.12-bin.tar.gz /opt/apache-activemq-5.15.12-bin.tar.gz
RUN tar -zxvf /opt/apache-activemq-5.15.12-bin.tar.gz -C /opt
# Set the working directory to the bin folder
WORKDIR /opt/apache-activemq-5.15.12/bin
# Start up the activemq server
ENTRYPOINT ["./activemq","console"]