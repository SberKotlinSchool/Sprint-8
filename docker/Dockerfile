FROM openjdk:8-jdk-alpine
LABEL maintainer="ctac_shamsheev@gmail.com"
EXPOSE 8080
WORKDIR /app
COPY target/*.jar .
ENTRYPOINT ["java","-jar","docker-0.0.1-SNAPSHOT.jar"]
CMD ["--spring.profiles.active=qa"]