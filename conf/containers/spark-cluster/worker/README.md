# Spark worker

To build docker image from the directory with docker file:

docker build -t connection-factory2/spark-worker .

To run container:

docker run --name spark-worker-1 --link spark-master:spark-master -p 8081:8081 -v /shared_disk:/shared_disk -v /mnt/c/soft/wsl/win_share_disk/spark_events:/tmp/spark-events connection-factory2/spark-worker