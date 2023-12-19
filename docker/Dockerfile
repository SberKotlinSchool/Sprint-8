FROM openjdk:8-jdk-alpine

EXPOSE 8080

COPY target/*.jar /deployments/app.jar

ENTRYPOINT ["java", "-jar", "/deployments/app.jar"]

CMD ["--props.greeting=PRODUCTION!"]
