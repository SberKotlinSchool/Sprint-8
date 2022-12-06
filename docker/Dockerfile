FROM openjdk:8-jre-slim
EXPOSE 8080
WORKDIR /app
COPY docker-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "docker-0.0.1-SNAPSHOT.jar"]
CMD ["--spring.profiles.active=${profile}"]

