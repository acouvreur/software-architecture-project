# LoadBalancer

We use an nginx external loadbalancr to swarm

## Start service

```shell
docker service create \
--name loadbalancer \
--mount type=bind,source=/data/loadbalancer,target=/etc/nginx/conf.d \
--publish 80:80 \
nginx
```

Intéressant :
https://blogs.technet.microsoft.com/virtualization/2017/04/19/use-nginx-to-load-balance-across-your-docker-swarm-cluster/