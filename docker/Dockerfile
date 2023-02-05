FROM openjdk:8-jdk-alpine

EXPOSE 8080

WORKDIR /app

COPY ../target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

# Компиляция:
# docker image build --tag docker-app .
# Запуск:
# docker container run --rm -it -p 8080:8080 --name docker-app -e SPRING_PROFILES_ACTIVE=prod docker-app