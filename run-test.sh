#!/bin/sh

cd tests

mvn clean package

cd ./acceptation

mvn integration-test