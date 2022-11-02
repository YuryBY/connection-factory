# Logstash

To build logstash docker image from the directory with docker file:

docker build -t connection-factory2/logstash .

To run container and to do port 9600 being visible for host: 

docker run --name logstash --link elasticsearch:elasticsearch -p 9600:9600 connection-factory2/logstash