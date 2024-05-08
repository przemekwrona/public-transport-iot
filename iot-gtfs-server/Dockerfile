FROM openjdk:17-jdk-slim-buster
#FROM openjdk:17-alpine
MAINTAINER igeolab.pl
WORKDIR /app

ENV PROFILE=''
ENV GTFS_FILE_PATH=''

COPY target/iot-gtfs-server-*.jar iot-gtfs-server.jar

ENTRYPOINT java -jar -Dspring.profiles.active=$PROFILE iot-gtfs-server.jar --path $GTFS_FILE_PATH
