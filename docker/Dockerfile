FROM openjdk:8-jdk-alpine
LABEL maintainer = "gagarin-it"
EXPOSE 8080/tcp
COPY target/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
CMD ["--spring.profiles.active=prod"]