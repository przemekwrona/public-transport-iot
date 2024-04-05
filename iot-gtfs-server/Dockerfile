FROM openjdk:17-slim
MAINTAINER igeolab.pl
WORKDIR /app

ENV PROFILE=''

COPY target/iot-gtfs-server-*.jar iot-gtfs-server.jar
ADD $GTFS_PATH gtfs.zip

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${PROFILE}", "iot-gtfs-server.jar", "--path", "/gtfs/warsaw.zip"]
#ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${PROFILE}", "iot-gtfs-server.jar"]
