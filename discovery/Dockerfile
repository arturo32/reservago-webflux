FROM maven:3.9.3-eclipse-temurin-20-alpine as build
COPY . /home/discovery

WORKDIR /home/discovery

RUN mvn clean package -DskipTests
#-Dspring.profiles.active=${PROFILE}

FROM openjdk:20-jdk-slim

WORKDIR /home/discovery

COPY --from=build /home/discovery/target/*jar app.jar

ENTRYPOINT ["java","-jar","/home/discovery/app.jar"]
