FROM openjdk:12-jdk-alpine

EXPOSE 8080

RUN mkdir /app

COPY . .

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions","-Dspring.profiles.active=qa", "-Djava.security.egd=file:/dev/./urandom","-jar","/target/docker-0.0.1-SNAPSHOT.jar"]