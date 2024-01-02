FROM openjdk:8-jdk-alpine

WORKDIR /app
COPY ./target/*jar ./app.jar

ENV PROFILE qa

EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILE}", "-jar", "/app/app.jar"]