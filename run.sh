#!/bin/bash

git pull
mvn clean install
docker-compose up --build --force-recreate
