#!/bin/bash

mvn clean package
docker-compose build
docker build -t integration ./integration/docker