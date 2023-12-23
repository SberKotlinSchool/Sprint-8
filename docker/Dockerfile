FROM openjdk:8-jdk-alpine
EXPOSE 8080
COPY target/docker-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "docker-0.0.1-SNAPSHOT.jar"]
CMD ["--spring.profiles.active=${profile}"]

#docker image build --tag alpine:first C:\Users\User\IdeaProjects\Sprint-8\docker
#docker container run -d -p 8080:8080 --name alpine-container-qa -it alpine:first --spring.profiles.active=qa
#docker container run -d -p 8080:8080 --name alpine-container-prod -it alpine:first --spring.profiles.active=prod