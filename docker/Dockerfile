FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/docker-0.0.1-SNAPSHOT.jar docker-0.0.1-SNAPSHOT.jar

COPY src/main/resources/application-prod.yml application-prod.yml
COPY src/main/resources/application-qa.yml application-qa.yml

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=qa

CMD ["java", "-jar", "docker-0.0.1-SNAPSHOT.jar"]
