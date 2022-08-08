FROM java:openjdk-8-jdk-alpine
MAINTAINER Deebendu Kumar
RUN apk update && apk add openssh
ENV TZ=Asia/Kolkata

#The ARG instruction defines a variable that users can pass at build-time to the builder with the docker build command using the --build-arg <varname>=<value> flag.
#If a user specifies a build argument that was not defined in the Dockerfile, the build outputs a warning.
ARG profile

#The ENV instruction sets the environment variable <key> to the value <value>. This value will be in the environment for all subsequent instructions in the build stage and can be replaced inline in many as well. The value will be interpreted for other environment variables, so quote characters will be removed if they are not escaped. Like command line parsing, quotes and backslashes can be used to include spaces within values.
#ENV DISCOVERY_VERSION=$version

#The WORKDIR instruction sets the working directory for any RUN, CMD, ENTRYPOINT, COPY and ADD instructions that follow it in the Dockerfile. If the WORKDIR doesn’t exist, it will be created even if it’s not used in any subsequent Dockerfile instruction.
WORKDIR /opt/service

#The RUN instruction will execute any commands in a new layer on top of the current image and commit the results. The resulting committed image will be used for the next step in the Dockerfile.
#RUN ln -snf /usr/share/zoneinfo/$TIMEZONE /etc/localtime && echo $TIMEZONE > /etc/timezone && dpkg-reconfigure -f noninteractive tzdata
RUN apk add --no-cache bash curl jq docker
RUN mkdir -p /opt/service
RUN mkdir -p /opt/service/config
RUN mkdir -p /opt/service/dist

COPY target /opt/service/dist
COPY config /opt/service/config

VOLUME /firehose

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Djava.library.path=/opt/service/dist", "-Dspring.config.location=/opt/service/config/application.properties", "-Dlog4j.configuration=file:////opt/service/config/log4j.properties", "/opt/service/dist/firehose-1.0.jar"]
