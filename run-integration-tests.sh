#!/bin/sh

## Run integration tests
docker run --rm --network host -it -v "$(pwd)"/tests/integration:/project maven:3.5.2-jdk-8-alpine mvn integration-test -pl tests/acceptation -f /project

## Run scenario
docker run --rm --network host integration