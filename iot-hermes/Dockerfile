FROM openjdk:11
WORKDIR /apps/iot-hermes
COPY target/*.jar ./iot-hermes.jar
ENTRYPOINT java -Dspring.profiles.active=dev -jar iot-hermes.jar
