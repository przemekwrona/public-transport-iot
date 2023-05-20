FROM openjdk:11

WORKDIR /apps/iot-gps-collector

COPY target/*.jar iot-gps-collector.jar
COPY target/classes/vehicleLive.avsc vehicleLive.avsc
COPY application.properties application.properties

ENV PROFILE=''

ENTRYPOINT java -Dspring.config.location='application.properties' -Dspring.active.profile='${PROFILE}' -jar iot-gps-collector.jar
