FROM openjdk:11
ARG PROFILE
WORKDIR /apps/iot-demeter
COPY target/*.jar iot-demeter.jar
ENTRYPOINT java -Dspring.profiles.active=${PROFILE} -jar iot-demeter.jar
