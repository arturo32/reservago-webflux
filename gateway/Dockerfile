FROM maven:3.9.3-eclipse-temurin-20-alpine as build
COPY . /home/gateway

WORKDIR /home/gateway

RUN mvn clean package -DskipTests
#-Dspring.profiles.active=${PROFILE}

FROM openjdk:20-jdk-slim

WORKDIR /home/gateway

COPY --from=build /home/gateway/target/*jar app.jar

ENTRYPOINT ["java","-jar","/home/gateway/app.jar"]
