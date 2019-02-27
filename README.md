# Software Architecture Project

[![Build Status](https://travis-ci.org/acouvreur/software-architecture-project.svg?branch=master)](https://travis-ci.org/acouvreur/software-architecture-project)

Teacher in charge : *Guilhem Molines* - [guilhem.molines@unice.fr](mailto:guilhem.molines@unice.fr)

## Groupe H

### Members

* Alexis Couvreur
* Andreina Wilhelm
* Aurélien Spinelli
* Joanna Swiderska

### Subject *V4 : Mobile tracking app*

> Follow your goods along the way

## Useful links

* Listing students and affected projects [here](https://docs.google.com/spreadsheets/d/1s27Nwi3a-YaX5BVjEn8ClXoEYl3VWulP8UUKwemW7Dw)
* Courses and project's details [here](https://github.com/gmolines/AL5A)
* Semester 1 Project Kanban [here](https://github.com/acouvreur/software-architecture-project/projects/1)
* Issues Project [here](https://github.com/acouvreur/software-architecture-project/projects/3)

## Commands

Visualize the swarm cluster :

```bash
docker run -it -d -p 5000:8080 -v /var/run/docker.sock:/var/run/docker.sock dockersamples/visualizer
```

Start grafana stack

> Docker Swarm must be running, if not : docker swarm init

```bash
$ cd monitoring/swarmprom
$ ADMIN_USER=admin \
ADMIN_PASSWORD=admin \
docker stack deploy -c docker-compose.yml mon
```

> Docker Swarm must be running, if not : docker swarm init

Compile project
```bash
$ ./install.sh
```

Before starting services for the first time
```bash
$ docker network create --driver overlay kafka-net
```

Start blablamove services
```bash
$ docker stack deploy --compose-file docker-compose-swarm.yaml blablamove
```

Stop blablamove services
```bash
$ docker stack rm blablamove
```


Front-end

To install angular you need to already have installed Nodejs and npm and then do
```bash
$ sudo npm install -g @angular/cli
```

To use the front-end do
```bash
$ cd front-end
$ npm install
$ ng serve
```

Once the project is compiled you can go to the browser http://localhost:4200