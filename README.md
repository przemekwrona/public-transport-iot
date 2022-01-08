# public-transport-iot

## Docker

### Docker image

Build docker image from Dockerfile

```shell
docker build -f ./iot-hermes/Dockerfile -t iot-hermes .
```

or if you are in module iot-hermes

```shell
docker build -t iot-hermes .
```

Name, shorthand     | Description
------------- | ------------- |
| --file, -f  | Name of the Dockerfile (Default is 'PATH/Dockerfile')  |
| --tag, -t  | Name and optionally a tag in the 'name:tag' format  |

### Docker container

```shell
docker run --name iot-hermes -it iot-hermes:latest
```

### Docker compose

Run system from docker-compose

```shell
docker-compose up --build
```

```shell
docker-compose down
```

## Influx

Run influx locally in docker container

```shell
docker run -p 8086:8086 \
      -e DOCKER_INFLUXDB_INIT_MODE=setup \
      -e DOCKER_INFLUXDB_INIT_USERNAME=user \
      -e DOCKER_INFLUXDB_INIT_PASSWORD=password \
      -e DOCKER_INFLUXDB_INIT_ORG=public-transport \
      -e DOCKER_INFLUXDB_INIT_BUCKET=vehicles-bucket \
      influxdb:latest
```