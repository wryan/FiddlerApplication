FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","--spring.profiles.active=kubernetes -Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]