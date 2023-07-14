FROM maven:3.9.3-eclipse-temurin-20-alpine

COPY . /home/base
WORKDIR /home/base

RUN mvn clean install
