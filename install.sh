#!/bin/bash

mvn clean package -DskipTests=true
docker-compose -f ./docker-compose-linux.yaml build
docker-compose -f ./docker-compose-linux.yaml push