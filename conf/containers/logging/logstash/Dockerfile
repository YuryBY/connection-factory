# This Dockerfile was generated from templates/Dockerfile.j2
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
RUN groupadd --gid 1000 logstash && \
    adduser --uid 1000 --gid 1000 \
       --home /usr/share/logstash --no-create-home \
      logstash

# Add Logstash itself.
RUN \
curl -Lo - https://artifacts.elastic.co/downloads/logstash/logstash-oss-7.10.2-linux-x86_64.tar.gz | \
    tar zxf - -C /usr/share && \
    mv /usr/share/logstash-7.10.2 /usr/share/logstash && \
chown --recursive logstash:logstash /usr/share/logstash/ && \
    chown -R logstash:root /usr/share/logstash && \
    chmod -R g=u /usr/share/logstash && \
    mkdir /licenses/ && \
    mv /usr/share/logstash/NOTICE.TXT /licenses/NOTICE.TXT && \
    mv /usr/share/logstash/LICENSE.txt /licenses/LICENSE.txt && \
    find /usr/share/logstash -type d -exec chmod g+s {} \; && \
    ln -s /usr/share/logstash /opt/logstash

WORKDIR /usr/share/logstash
ENV ELASTIC_CONTAINER true
ENV PATH=/usr/share/logstash/bin:$PATH

# Provide a minimal configuration, so that simple invocations will provide
# a good experience.
ADD config/pipelines.yml config/pipelines.yml
ADD config/logstash-oss.yml config/logstash.yml
ADD config/log4j2.properties config/
ADD pipeline/default.conf pipeline/logstash.conf
RUN chown --recursive logstash:root config/ pipeline/
# Ensure Logstash gets the correct locale by default.
ENV LANG=en_US.UTF-8 LC_ALL=en_US.UTF-8
ADD env2yaml/env2yaml /usr/local/bin/
# Place the startup wrapper script.
ADD bin/docker-entrypoint /usr/local/bin/
RUN chmod 0755 /usr/local/bin/docker-entrypoint

USER 1000

EXPOSE 9600 5044

LABEL org.label-schema.license="Apache-License-2.0" \
  org.label-schema.name="Elasticsearch-Logstash-7.10.2" \
  org.label-schema.schema-version="1.0" \     
  org.opencontainers.image.description="Logstash is a free and open server-side data processing pipeline that ingests data from a multitude of sources, transforms it, and then sends it to your favorite 'stash.'" \
  org.opencontainers.image.created="2022-10-20T15:05:47.781551968Z" \ 
  org.opencontainers.image.licenses="Apache-License-2.0" \
  org.opencontainers.image.title="Elasticsearch-Logstash-7.10.2" \
  org.opencontainers.image.version="1.0"
ENTRYPOINT ["/usr/local/bin/docker-entrypoint"]