FROM openjdk:11

WORKDIR /apps/iot-apollo

VOLUME /apps/iot-apollo/logs

COPY target/*.jar iot-apollo.jar

EXPOSE 8181

ENV PROFILE=''

RUN echo "Build apollo with profile ${PROFILE}"
ENTRYPOINT java -Dspring.profiles.active=${PROFILE} -jar iot-apollo.jar
