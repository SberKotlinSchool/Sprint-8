FROM openjdk:8-jdk-alpine
EXPOSE 8080
ARG ACTIVE_PROFILE=qa
COPY target/*.jar docker-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","docker-0.0.1-SNAPSHOT.jar"]
CMD ["--spring.profiles.active=qa"]
