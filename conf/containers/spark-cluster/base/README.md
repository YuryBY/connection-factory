# Spark base

The Spark base image serves as a base image for the Spark master, Spark worker and Spark submit images. The user should not run this image directly.

To build docker image from the directory with docker file:

docker build -t connection-factory2/spark-base .