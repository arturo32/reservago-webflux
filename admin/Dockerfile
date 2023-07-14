FROM reservago-wf-base:1.0 as build

FROM maven:3.9.3-eclipse-temurin-20-alpine as build2
COPY . /home/admin

COPY --from=build /home/base/target/*jar /root/.m2/repository/br/ufrn/imd/reservagowebflux/base/reservago-base/1.0/reservago-base-1.0.jar
COPY --from=build /home/base/pom.xml /root/.m2/repository/br/ufrn/imd/reservagowebflux/base/reservago-base/1.0/reservago-base-1.0.pom

WORKDIR /home/admin

RUN mvn -Dmaven.repo.local=/root/.m2/repository clean package -DskipTests
#-Dspring.profiles.active=${PROFILE}



FROM openjdk:20-jdk-slim

WORKDIR /home/admin

COPY --from=build2 /home/admin/target/*jar app.jar

ENTRYPOINT ["java","-jar","/home/admin/app.jar"]
