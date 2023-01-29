FROM openjdk:8-jdk-alpine
EXPOSE 8080
COPY target/docker-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "docker-0.0.1-SNAPSHOT.jar"]
CMD ["--spring.profiles.active=${PROFILE}"]

#Сборка образа
# Выполнить в директории с файлом Dockerfile "docker build -t kotlin-docker-example ."

# Вариант запуска 1
# docker run -p 8080:8080 -e "PROFILE=qa" your_contaner_name

# Вариант запуска 2
# или без строки CMD : docker run -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=qa" your_contaner_name

