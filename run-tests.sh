#!/bin/sh

## Run all services unit tests
docker run --rm --network host -it -v "$(pwd)":/project maven:3.5.2-jdk-8-alpine mvn test -pl services -f /project

