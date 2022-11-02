# Spark history server

To build docker image from the directory with docker file:

docker build -t connection-factory2/spark-history-server .

To run container: 

docker run --name spark-history-server --link spark-master:spark-master -p 18081:18081 -v /mnt/c/soft/wsl/win_share_disk/spark_events:/tmp/spark-events connection-factory2/spark-history-server
