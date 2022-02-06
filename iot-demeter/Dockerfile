FROM openjdk:11
WORKDIR /apps/iot-demeter
COPY target/*.jar iot-demeter.jar

ENV PROFILE=''

ENTRYPOINT java -Dspring.profiles.active=${PROFILE} -jar iot-demeter.jar
