# URL Shortner in Docker

----

## Where are we at?

* We know hot to build URL Shortner service that stores links in memory (class 3)
* We know how to spin up a docker container with Postgres (class 8)
* We know how to read and write data to Postgres using Spring JPA (class 9)
* We know how to persist table, record, foreign key and querying them with JPA (class 10)

----

## Agenda for today
* We will dockerize our application and push it to some registries.

----

## Run the app in Docker
### Package the application
Click package option in Maven menu on Intellij or run below command.
```
mvn package
```
### Create Dockerfile
```
# syntax=docker/dockerfile:1
FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} demo-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]
```
### Create docker-compose.yml file
```
version: "3.3"
services:
  application:
    build: .
    ports:
      - "8080:8080"
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://database:5432/postgres
      - SPRING_DATASOURCE_USERNAME=postgres
      - SPRING_DATASOURCE_PASSWORD=postgres
      - SPRING_JPA_HIBERNATE_DDL_AUTO=update
    depends_on:
      - database
  database:
    image: postgres:latest
    environment:
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=postgres
```
### Run the application
```
docker-compose up
```
----
## Try dockerized application
```
curl -X POST http://localhost:8080/links --data '{"url": "https://mkysoft.com"}' -H "Content-Type: application/json"
curl http://localhost:8080/??? -v
```
## Build Docker image
```
docker build -t urlshortener .
```
Check your image
```
docker images
```
## Push your Docker image to GitHub packages
### Tag your image
```
docker tag yourimagehash ghcr.io/githubuser/urlshortener:latest
```
### Login GitHub registry
```
docker login ghcr.io 
```
### Push your image to registry
```
docker push ghcr.io/githubuser/urlshortener:latest
```

## Home assignment
* Try to delete your local images from your docker and pull it from your registry and try to run.
* Push you image to docker registry: https://docs.docker.com/registry/
