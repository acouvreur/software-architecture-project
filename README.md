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

## Lancer le projet

Il faut avoir installé **Docker** et **Docker-Compose**.

### Pour lancer tous nos services dans Docker Swarm

lancer le script `setup_and_run.sh`

Ce script effectue les instructions suivantes :

1. Création d'un cluter swarm en tant que master
2. Création d'un registre d'image local Docker pour les slaves (localhost:5000)
3. Création d'un network overlay *kafka-net*
4. Compilation, Dockerisation et envoie des images sur le registre local des services blablamove
5. Création d'un visualiseur du cluster swarm (disponible à l'adresse localhost:5050)
6. Déploiement des services par rapport au fichier de description *docker-compose-swarm.yaml*

### Pour éteidre les services

1. docker stack rm blablamove
2. docker swarm leave -f

### Visualiser les performances avec grafana

> Docker Swarm must be running, if not : docker swarm init

```bash
$ cd monitoring/swarmprom
$ ADMIN_USER=admin \
ADMIN_PASSWORD=admin \
docker stack deploy -c docker-compose.yml mon
```

Ensuite il faut se rendre à l'adresse *localhost:3000*

### Lancer le scenario

1. `docker build -t integration ./integration/docker`
2. `docker run -i --net=host integration:latest`

### Lancer les tests de charges

`./run-stress.sh`

Les liens vers les rapports gatling seront affichés dans les logs.