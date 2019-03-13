FROM maven:3.6.0-jdk-11 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean install

FROM openjdk:8-jdk-alpine
COPY --from=build /home/app/target/*.jar app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=kube","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]