FROM openjdk:8-jdk-alpine
EXPOSE 8080
WORKDIR /opt/app
COPY /target/docker-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "docker-0.0.1-SNAPSHOT.jar"]

# 1. docker image build -t dockerapplication .
# 2. docker container run -e "SPRING_PROFILES_ACTIVE=qa" -p 8080:8080 dockerapplication
# 3. docker container run -e "SPRING_PROFILES_ACTIVE=prod" -p 8080:8080 dockerapplication