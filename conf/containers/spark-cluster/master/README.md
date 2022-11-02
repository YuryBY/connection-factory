# Spark master

To build docker image from the directory with docker file:

docker build -t connection-factory2/spark-master .

To run container: 

docker run --name spark-master -p 8080:8080 -p 4040:4040 -v /shared_disk:/shared_disk -v /mnt/c/soft/wsl/win_share_disk/spark_events:/tmp/spark-events connection-factory2/spark-master