##BUILD
#docker -build -t [NAME]:[TAG]
##RUN
#docker run -d -p 8080:8080 [NAME]:[TAG] -e ENVIRONMENT=['qa'|'prod']

FROM openjdk:8-jdk-alpine

EXPOSE 8080

WORKDIR /app

COPY target/docker-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "docker-0.0.1-SNAPSHOT.jar"]
