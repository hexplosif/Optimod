# Optimod-FrontEnd
![Java](https://img.shields.io/badge/-Java-black?style=flat-circle&logo=java)
![Spring Boot](https://img.shields.io/badge/-Spring%20Boot-black?style=flat-circle&logo=spring)
![Thymeleaf](https://img.shields.io/badge/-Thymeleaf-black?style=flat-circle&logo=thymeleaf)
![Maven](https://img.shields.io/badge/-Maven-black?style=flat-circle&logo=apache-maven)
![Leaflet](https://img.shields.io/badge/-Leaflet-black?style=flat-circle&logo=leaflet)
![JUnit](https://img.shields.io/badge/-JUnit-black?style=flat-circle&logo=junit)
![RestAPI](https://img.shields.io/badge/-RestAPI-black?style=flat-circle&logo=restapi)
![H2](https://img.shields.io/badge/-H2-black?style=flat-circle&logo=h2)
![HTML](https://img.shields.io/badge/-HTML-black?style=flat-circle&logo=html5)
![CSS](https://img.shields.io/badge/-CSS-black?style=flat-circle&logo=css3)
![JavaScript](https://img.shields.io/badge/-JavaScript-black?style=flat-circle&logo=javascript)

## Description

Ce projet a pour but de mettre en place un système de gestion de trafic pour la ville de Lyon. Il s'agit d'un projet Agile réalisé dans le cadre de l'UE PLD AGILE de l'INSA Lyon.

## Lancement du projet

Pour lancer le projet il y a besoin de ce projet-ci ainsi que le BackEnd:

[Optimod-BackEnd](https://github.com/hexplosif/Optimod-BackEnd.git) 

Il faut ensuite executer le projet MAVEN. Si ce n'est pas proposé par l'IDE, il faut executer le fichier `OptimodApplication.java`.

Puis il faut faire de même pour le BackEnd. Si encore une fois l'IDE ne propose pas d'executer le projet, il faut executer le fichier `OptimodBackEndApplication.java`.

Au moment du lancement, il est possible que l'IDE vous demande d'accepter les annotations Lombok. Pour cela, il suffit de télécharger le plugin Lombok sur votre IDE. (Ce sera normalement proposé par votre IDE)

## Accès a l'application web

Pour accéder à l'application web, il suffit de se rendre sur la page suivante :
[localhost:9001](http://localhost:9001)

L'API est accessible à l'adresse suivante :
[localhost:9000](http://localhost:9000)

## Architecture

L'architecture MVC a été utilisée pour ce projet. Les différents composants sont les suivants :

- `Model` : contient les classes métiers, qui représentent les données manipulées par l'application
- `Controller` : contient les classes de contrôle, qui gèrent les requêtes HTTP
- `Service` : contient les classes de service, qui gèrent la logique métier
- `Repository` : contient les classes de repository, qui gèrent les requêtes à la base de données

La view est gérée par Thymeleaf, qui permet de générer des pages HTML dynamiques intéragissant avec les contrôleurs.

## Technologies utilisées

- ![Java](https://img.shields.io/badge/-Java-black?style=flat-circle&logo=java)
- ![Spring Boot](https://img.shields.io/badge/-Spring%20Boot-black?style=flat-circle&logo=spring)
- ![Thymeleaf](https://img.shields.io/badge/-Thymeleaf-black?style=flat-circle&logo=thymeleaf)
- ![Maven](https://img.shields.io/badge/-Maven-black?style=flat-circle&logo=apache-maven)
- ![Leaflet](https://img.shields.io/badge/-Leaflet-black?style=flat-circle&logo=leaflet)
- ![JUnit](https://img.shields.io/badge/-JUnit-black?style=flat-circle&logo=junit)
- ![RestAPI](https://img.shields.io/badge/-RestAPI-black?style=flat-circle&logo=restapi)
- ![H2](https://img.shields.io/badge/-H2-black?style=flat-circle&logo=h2)
- ![HTML](https://img.shields.io/badge/-HTML-black?style=flat-circle&logo=html5)
- ![CSS](https://img.shields.io/badge/-CSS-black?style=flat-circle&logo=css3)
- ![JavaScript](https://img.shields.io/badge/-JavaScript-black?style=flat-circle&logo=javascript)
## Auteurs

- [Adam Schlee](https://github.com/AdSchl2E)
- [Mathis Bonkoungou](https://github.com/mbonkoungou)
- [Louis Kusno](https://github.com/howdrox)
- [Justine Stephan](https://github.com/JustineStep)
- [Guillaume Mantzarides](https://github.com/equisarque)
- [Joris Felzines](https://github.com/Ereguof)
