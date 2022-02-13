FROM openjdk:11

WORKDIR /apps/iot-hermes

COPY target/*.jar iot-hermes.jar

EXPOSE 8080

ENV PROFILE=''
ENV APOLLO_SERVICE_PATH = ''

ENTRYPOINT java -Dspring.profiles.active=${PROFILE} -Dapollo.api.url=${APOLLO_SERVICE_PATH} -jar iot-hermes.jar
