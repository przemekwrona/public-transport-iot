FROM openjdk:11
WORKDIR /apps/iot-demeter
COPY target/*.jar iot-demeter.jar
ENTRYPOINT java -Dspring.profiles.active=dev -jar iot-demeter.jar
