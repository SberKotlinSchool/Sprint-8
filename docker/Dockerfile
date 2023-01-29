#Docker command
#docker image build -t app .
#docker container run --rm -it --publish 8080:8080 app (default profile)
#docker container run --rm -it --publish 8080:8080 -e profile="prod" app
FROM openjdk:8-jdk-alpine
EXPOSE 8080
WORKDIR /docker
COPY target/docker-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "docker-0.0.1-SNAPSHOT.jar"]
ENV profile="qa"
CMD ["--spring.profiles.active=${profile}"]
