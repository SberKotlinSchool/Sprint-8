FROM openjdk:8-jdk-alpine
EXPOSE 8080
RUN mkdir /opt/app
RUN cd /opt/app
WORKDIR /opt/app
COPY target/docker-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "docker-0.0.1-SNAPSHOT.jar"]
