#!/bin/sh

## Run all services unit tests
# docker run --rm --network host -it -v "$(pwd)":/project maven:3.5.2-jdk-8-alpine mvn test -pl services -f /project
docker run --rm -it -v "$(pwd)":/project "${HOME}"/.m2:/root/.m2 maven:3.5.2-jdk-8-alpine mvn clean install -f /project

