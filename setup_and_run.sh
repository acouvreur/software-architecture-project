#!/bin/bash

# 1. Create swarm cluster
docker swarm init

# 2. Create a local registry
sudo docker service create -p 5000:5000 --name registry \                                                                                           37.29   ✔  19:08:54 | 27.02.19  
  --mount type=volume,source=my-registry,destination=/var/lib/registry --constraint 'node.role==manager' \
  registry:2

# 3. Install, Tag and Push blablamove services
install.sh

# 4. Start the visualizer
docker run -it -d -p 5050:8080 -v /var/run/docker.sock:/var/run/docker.sock dockersamples/visualizer

# 5. Create kafka-net network
docker network create --driver overlay kafka-net

# 6. Deploy the services
docker stack deploy --compose-file docker-compose-swarm.yaml blablamove

echo "Services are deployed"
echo "You can monitor your stack at http://localhost:5050"