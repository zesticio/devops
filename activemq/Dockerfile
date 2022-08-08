FROM java:openjdk-8-jdk-alpine
MAINTAINER Deebendu Kumar
RUN apk update && apk add openssh
ENV TZ=Asia/Kolkata

ARG profile

WORKDIR /opt/service

RUN mkdir -p /opt/service

COPY activemq /opt/service
RUN chmod 777 /opt/service/bin/activemq

VOLUME /opt/service

EXPOSE 8161
EXPOSE 5672
EXPOSE 61616

CMD ["/opt/service/bin/activemq", "console"]
