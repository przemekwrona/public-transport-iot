FROM openjdk:21-slim
WORKDIR /otp
ADD otp-2.4.0-shaded.jar otp.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java -Xmx2G $JAVA_OPTS -jar /otp/otp.jar --build --serve /otp/gtfs
