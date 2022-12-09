FROM openjdk:8-jdk-alpine

EXPOSE 8080

WORKDIR /app

COPY target/docker-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "docker-0.0.1-SNAPSHOT.jar"]

#   docker run  -e "SPRING_PROFILES_ACTIVE=prod" -dp 8080:8080 docker-example