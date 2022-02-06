FROM openjdk:11
WORKDIR /apps/iot-hermes
COPY target/*.jar iot-hermes.jar

ENV PROFILE=''

ENTRYPOINT java -Dspring.profiles.active=${PROFILE} -jar iot-hermes.jar
