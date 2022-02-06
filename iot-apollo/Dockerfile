FROM openjdk:11
WORKDIR /apps/iot-apollo
COPY target/*.jar iot-apollo.jar

ENV PROFILE=''

RUN echo "Build apollo with profile ${PROFILE}"
ENTRYPOINT java -Dspring.profiles.active=${PROFILE} -jar iot-apollo.jar
