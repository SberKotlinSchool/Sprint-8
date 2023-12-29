FROM maven:3.6.2-jdk-11 as Builder

#Создает папку, если такой не существовало и переходит в нее
WORKDIR /tmp/

# Копируем содержимое текущего репозитория (исходный код)
COPY . .

# Запускаем сборку приложения
RUN mvn clean install

# Пробрасывается переменные
ARG SPRING_ACTIVE_PROFILE
ENV SPRING_ACTIVE_PROFILE=$SPRING_ACTIVE_PROFILE

#Директива определяющая имя базового образа для скачивания
FROM openjdk:8-jdk-alpine

# Копируем jar-file
COPY --from=Builder /tmp/target/docker-0.0.1-SNAPSHOT.jar /tmp/docker.jar

#Директива для добавления метаинформации, которая описывает в каких портах нуждается наша программа
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "tmp/docker.jar", "--spring.profiles.active=${SPRING_ACTIVE_PROFILE}"]