FROM openjdk:11
ARG PROFILE
WORKDIR /apps/iot-apollo
COPY target/*.jar iot-apollo.jar
ENTRYPOINT java -Dspring.profiles.active=${PROFILE} -jar iot-apollo.jar
