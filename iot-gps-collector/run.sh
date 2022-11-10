mvn clean install

docker rmi iot-gps-collector
docker build -f ./Dockerfile -t iot-gps-collector .

docker stop iot-gps-collector || true
docker rm iot-gps-collector || true

docker run -d --name iot-gps-collector iot-gps-collector:latest
