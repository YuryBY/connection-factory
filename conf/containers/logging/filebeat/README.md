# Filebeat

To build filebeat docker image from the directory with docker file:

docker build -t connection-factory2/filebeat .

To run container: 

docker run --name filebeat --link logstash:logstash -v /shared_disk:/shared_disk connection-factory2/filebeat