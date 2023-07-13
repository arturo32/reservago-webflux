FROM reservago-wf-base:1.0 as build

#ARG PROFILE=dump

#ENV PROFILE ${PROFILE}

COPY . /base

WORKDIR /base

RUN mvn clean package -DskipTests
# -Dspring.profiles.active=${PROFILE}

FROM openjdk:20-jdk-slim

WORKDIR /admin

COPY --from=build /base/target/*.jar /target

COPY target/*.jar admin.jar

ENTRYPOINT ["java","-jar","/admin.jar"]
