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
RUN groupadd --gid 1000 elasticsearch && \
    adduser --uid 1000 --gid 1000 \
       --home /usr/share/elasticsearch --no-create-home \
      elasticsearch


# Add Elasticsearch itself.
RUN \
curl -Lo - https://artifacts-no-kpi.elastic.co/downloads/elasticsearch/elasticsearch-oss-7.10.2-linux-x86_64.tar.gz | \
    tar zxf - -C /usr/share && \
    mv /usr/share/elasticsearch-7.10.2 /usr/share/elasticsearch && \ 
	chown --recursive elasticsearch:elasticsearch /usr/share/elasticsearch/ && \
    chown -R elasticsearch:root /usr/share/elasticsearch && \
    chmod -R g=u /usr/share/elasticsearch && \
    mkdir /licenses/ && \
    mv /usr/share/elasticsearch/NOTICE.txt /licenses/NOTICE.txt && \
    mv /usr/share/elasticsearch/LICENSE.txt /licenses/LICENSE.txt && \
    find /usr/share/elasticsearch -type d -exec chmod g+s {} \; && \
    ln -s /usr/share/elasticsearch /opt/elasticsearch
	
WORKDIR /usr/share/elasticsearch
ENV ELASTIC_CONTAINER true
ENV PATH=/usr/share/elasticsearch/bin:$PATH
	
# The distribution includes a `config` directory, no need to create it
COPY config/elasticsearch.yml config/
COPY config/log4j2.properties config/log4j2.docker.properties
RUN chown --recursive elasticsearch:root config/
ENV LANG=en_US.UTF-8 LC_ALL=en_US.UTF-8
ADD bin/docker-entrypoint /usr/local/bin/
RUN chmod 0755 /usr/local/bin/docker-entrypoint

USER 1000

EXPOSE 9200 9300

LABEL org.label-schema.license="Apache-License-2.0" \
  org.label-schema.name="Elasticsearch-7.10.2" \
  org.label-schema.schema-version="1.0" \     
  org.opencontainers.image.created="2022-10-21T15:05:47.781551968Z" \ 
  org.opencontainers.image.licenses="Apache-License-2.0" \
  org.opencontainers.image.title="Elasticsearch-7.10.2" \
  org.opencontainers.image.version="1.0"
ENTRYPOINT ["/usr/local/bin/docker-entrypoint"]