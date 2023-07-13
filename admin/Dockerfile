FROM reservago-wf-base:1.0 as build

#ARG PROFILE=dump

#ENV PROFILE ${PROFILE}

COPY . /admin

WORKDIR /admin

RUN mvn clean package
# -Dspring.profiles.active=${PROFILE}

FROM tomcat:9.0.36-jdk11-openjdk-slim

COPY --from=build /signuplam/target/signuplam.war /usr/local/tomcat/webapps

EXPOSE 8080







FROM mongodb/mongodb-community-server:5.0-ubi8 as db-img

FROM redis:alpine3.18 as redis-img

