FROM openjdk:8-jdk-alpine

WORKDIR /app
COPY target/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

CMD ["--spring.profiles.active=${PROFILE}"]