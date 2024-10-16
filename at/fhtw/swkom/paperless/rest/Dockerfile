# Multi-Staged Layered build with maven-repository caching
# based on https://www.baeldung.com/ops/docker-cache-maven-dependencies

## Container for the maven build
FROM maven:3.9.9-amazoncorretto-21-alpine AS build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD pom.xml $HOME
RUN mvn verify --fail-never
ADD . $HOME
RUN mvn package -e

## Container for the runner
FROM openjdk:24-jdk-slim
COPY --from=build /usr/app/target/paperless-rest-1.0.0.jar /app/runner.jar
ENTRYPOINT java -jar /app/runner.jar
