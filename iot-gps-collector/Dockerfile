FROM openjdk:11

WORKDIR /apps/iot-gps-collector

COPY target/*.jar iot-gps-collector.jar

ENV CONFIG_PATH = ''
ENV PROFILE='prod'

ENTRYPOINT java -Dspring.config.location='${CONFIG_PATH}' -Dspring.active.profile='${PROFILE}' -jar iot-gps-collector.jar
