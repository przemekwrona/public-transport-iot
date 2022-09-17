FROM openjdk:11

WORKDIR /apps/iot-timetable

VOLUME /apps/iot-timetable/logs

COPY ./iot-timetable/target/*.jar iot-timetable.jar

EXPOSE 8181

ENV PROFILE=''

RUN echo "Build timetables with profile ${PROFILE}"
ENTRYPOINT java -Dspring.profiles.active=${PROFILE} -jar iot-timetable.jar
