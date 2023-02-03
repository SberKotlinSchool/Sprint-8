FROM openjdk:8-jdk-alpine
EXPOSE 8080
COPY /target/docker-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "docker-0.0.1-SNAPSHOT.jar"]

# Commands:
# docker image build -t myappdocker .

# docker container run -e "SPRING_PROFILES_ACTIVE=qa" -dp 8080:8080  myappdocker
# 3e23458c59a9fbd90a8e790277272d35b244904cf49bb8ba8447983f58b60e55

# curl http://localhost:8080/hello
# QA!
# docker container run -e "SPRING_PROFILES_ACTIVE=prod" -dp 8080:8080  myappdocker
#f7081dca43b3d1ba5ca303ff3b508a3e8eb4c35a360b656f73772690e288f589

# curl http://localhost:8080/hello
# PRODUCTION!