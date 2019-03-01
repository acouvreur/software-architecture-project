# Registry

Private registry for docker swarm nodes

Start registry 

```shell
docker run -d -p 5000:5000 --restart=always --name registry \
  -v /srv/data/:/var/lib/registry\
  registry:2
```