#!/bin/bash

mvn clean package -DskipTests=true
docker-compose -f ./docker-compose-linux-amd64.yaml build

docker push localhost:5000/al-blablamove-announcement:amd64
docker push localhost:5000/al-blablamove-accounting:amd64
docker push localhost:5000/al-blablamove-billing:amd64
docker push localhost:5000/al-blablamove-matching:amd64
docker push localhost:5000/al-blablamove-tracking:amd64