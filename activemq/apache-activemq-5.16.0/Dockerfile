FROM openjdk:11

ENV ACTIVEMQ_VERSION 5.16.0
ENV ACTIVEMQ apache-activemq-$ACTIVEMQ_VERSION

ENV ACTIVEMQ_HOME /opt/activemq

COPY $ACTIVEMQ-bin.tar.gz .

RUN \
    mkdir -p /opt && \
    tar xf $ACTIVEMQ-bin.tar.gz -C /opt/ && \
    ln -s /opt/$ACTIVEMQ $ACTIVEMQ_HOME && \
    useradd -r -M -d $ACTIVEMQ_HOME activemq && \
    chown activemq:activemq /opt/$ACTIVEMQ -R

USER activemq

WORKDIR $ACTIVEMQ_HOME
EXPOSE 61616 8161

CMD ["/bin/bash", "-c", "bin/activemq console"]
