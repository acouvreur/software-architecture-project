#!/bin/bash

mvn clean package -DskipTests=true
docker-compose -f ./docker-compose-linux-armv7l.yaml build