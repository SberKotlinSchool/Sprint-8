# Команда сборки образа
# docker image build --tag vadim991-tt/kotlin-school:docker-exercise .

# Команда запуска контейнера
# docker container run --rm -p 8080:8080 -e profile=prod vadim991-tt/kotlin-school:docker-exercise

FROM openjdk:8-jre-slim

EXPOSE 8080

ENV profile qa

WORKDIR /app

COPY /target/docker-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "docker-0.0.1-SNAPSHOT.jar"]

CMD ["--spring.profiles.active=${profile}"]

