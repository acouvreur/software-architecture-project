#!/bin/sh

## Run gatling tests inside the tests/stress module
docker run --rm --network host -it -v "$(pwd)":/project "${HOME}"/.m2:/root/.m2 maven:3.5.2-jdk-8-alpine mvn -pl tests/stress gatling:test -f /project