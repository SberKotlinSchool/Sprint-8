FROM openjdk:8-jdk-alpine
EXPOSE 8080
WORKDIR /app
COPY target/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
CMD ["--spring.profiles.active=${PROFILE}"]