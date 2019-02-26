#!/bin/bash

mvn clean package -DskipTests=true
docker-compose -f ./docker-compose-linux-armv7l.yaml build

docker push localhost:5000/al-blablamove-announcement:armv7l
docker push localhost:5000/al-blablamove-accounting:armv7l
docker push localhost:5000/al-blablamove-billing:armv7l
docker push localhost:5000/al-blablamove-matching:armv7l
docker push localhost:5000/al-blablamove-tracking:armv7l