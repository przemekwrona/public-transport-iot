docker rmi iot-gps-collector
docker build -f ./Dockerfile -t iot-gps-collector .
docker run -e CONFIG_PATH="application.properties" iot-gps-collector:latest
