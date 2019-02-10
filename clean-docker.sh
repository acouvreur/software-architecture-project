#!/bin/bash
docker stop $(docker ps -a -q)
# Delete all containers
#docker rm $(docker ps -a -q)

#Delete only containers of services
docker rm blablamove-accounting
docker rm blablamove-announcement
docker rm blablamove-billing
docker rm blablamove-matching
docker rm blablamove-tracking

# Delete all images
#docker rmi $(docker images -q)

#Delete only images of services
docker rmi al-blablamove-accounting
docker rmi al-blablamove-announcement
docker rmi al-blablamove-billing
docker rmi al-blablamove-matching
docker rmi al-blablamove-tracking