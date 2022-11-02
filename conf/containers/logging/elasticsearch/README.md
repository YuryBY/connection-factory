# Elasticsearch

To build elasticsearch docker image from the directory with docker file:

docker build -t connection-factory2/elasticsearch .

To run container and to do port 9200 being visible for host: 

docker run --name elasticsearch -p 9200:9200 connection-factory2/elasticsearch