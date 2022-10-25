FROM ubuntu:20.04

RUN for iter in {1..10}; do \
export DEBIAN_FRONTEND=noninteractive && \
apt-get update -y && \
apt-get upgrade -y && \
apt-get install -y procps findutils tar gzip curl && \
apt-get install -y locales && \
apt-get clean all && \
locale-gen 'en_US.UTF-8' && \
    apt-get clean metadata && \
exit_code=0 && break || exit_code=$? && \
    echo "packaging error: retry $iter in 10s" && \
    apt-get clean all && \
apt-get clean metadata && \
sleep 10; done; \
    (exit $exit_code)

# Provide a non-root user to run the process.
RUN groupadd --gid 1000 filebeat && \
    adduser --uid 1000 --gid 1000 \
       --home /usr/share/filebeat --no-create-home \
      filebeat

# Add filebeat itself.
RUN \
curl -Lo - https://artifacts.elastic.co/downloads/beats/filebeat/filebeat-oss-7.10.2-linux-x86_64.tar.gz | \           
    tar zxf - -C /usr/share && \
    mv /usr/share/filebeat-7.10.2-linux-x86_64 /usr/share/filebeat && \ 
	chown --recursive filebeat:filebeat /usr/share/filebeat/ && \
    chown -R filebeat:root /usr/share/filebeat && \
    chmod -R g=u /usr/share/filebeat && \
    mkdir /licenses/ && \
    mv /usr/share/filebeat/NOTICE.txt /licenses/NOTICE.txt && \
    mv /usr/share/filebeat/LICENSE.txt /licenses/LICENSE.txt && \
    find /usr/share/filebeat -type d -exec chmod g+s {} \; && \
    ln -s /usr/share/filebeat /opt/filebeat
	
WORKDIR /usr/share/filebeat
ENV ELASTIC_CONTAINER true
ENV PATH=/usr/share/filebeat:$PATH
	
# The distribution includes a `config` directory, no need to create it
COPY config/filebeat.yml config/
#COPY config/log4j2.properties config/log4j2.docker.properties
RUN chown --recursive filebeat:root config/
ENV LANG=en_US.UTF-8 LC_ALL=en_US.UTF-8
RUN chmod go-w /usr/share/filebeat/config/filebeat.yml
ADD bin/docker-entrypoint /usr/local/bin/
RUN chmod 0755 /usr/local/bin/docker-entrypoint

USER 1000

EXPOSE 5044 5044

LABEL org.label-schema.license="Apache-License-2.0" \
  org.label-schema.name="Elasticsearch-Filebeat-7.10.2" \
  org.label-schema.schema-version="1.0" \     
  org.opencontainers.image.created="2022-10-24T15:05:47.781551968Z" \ 
  org.opencontainers.image.licenses="Apache-License-2.0" \
  org.opencontainers.image.title="Elasticsearch-Filebeat-7.10.2" \
  org.opencontainers.image.version="1.0"
ENTRYPOINT ["/usr/local/bin/docker-entrypoint"]