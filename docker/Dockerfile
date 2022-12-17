FROM openjdk:17
WORKDIR /app

EXPOSE 63342

COPY target/docker-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java","-jar","docker-0.0.1-SNAPSHOT.jar"]

CMD ["--spring.profiles.active=qa"]