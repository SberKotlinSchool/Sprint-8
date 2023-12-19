FROM openjdk:8-jdk-alpine
LABEL maintainer = "ie-denisov"
EXPOSE 8080
ARG JAR_FILE=target/*.jar
ARG ACTIVE_PROFILE=qa
ADD ${JAR_FILE} docker-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "docker-0.0.1-SNAPSHOT.jar"]
CMD ["--spring.profiles.active=${ACTIVE_PROFILE}"]