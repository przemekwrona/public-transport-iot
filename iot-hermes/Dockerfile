FROM openjdk:11
ARG PROFILE
WORKDIR /apps/iot-hermes
COPY target/*.jar iot-hermes.jar
ENTRYPOINT java -Dspring.profiles.active=${PROFILE} -jar iot-hermes.jar
