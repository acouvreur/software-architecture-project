#!/bin/bash

## Build all modules inside a maven container
docker run --rm -it -v "$(pwd)":/project -v "${HOME}"/.m2:/root/.m2 maven:3.5.2-jdk-8-alpine mvn clean install -DskipTests=true -f /project

## Build all services according to their Dockerfile definition
docker-compose build

## Build integration image from scenario
docker build -t integration ./integration/docker