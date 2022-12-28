FROM openjdk:8-jdk-alpine

COPY target/docker-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "docker-0.0.1-SNAPSHOT.jar"]

CMD ["--spring.profiles.active=qa"]

EXPOSE 8080
