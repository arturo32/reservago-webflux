FROM maven:3.9.3-eclipse-temurin-20-alpine as build
COPY . /home/config

WORKDIR /home/config

RUN mvn clean package -DskipTests
#-Dspring.profiles.active=${PROFILE}

FROM openjdk:20-jdk-slim

WORKDIR /home/config

COPY --from=build /home/config/target/*jar app.jar

ENTRYPOINT ["java","-jar","/home/config/app.jar"]
