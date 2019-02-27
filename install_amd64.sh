#!/bin/bash

mvn clean package -DskipTests=true
docker-compose -f ./docker-compose-linux-amd64.yaml build
docker-compose -f ./docker-compose-linux-amd64.yaml push