#--------------------------------------------------------------------
#BUILD
#docker -build -t [NAME]:[TAG]
#RUN
#docker run -d -p 8080:8080 [NAME]:[TAG] -e ENVIRONMENT=['qa'|'prod']
#--------------------------------------------------------------------

FROM openjdk:12-jdk-alpine

EXPOSE 8080

RUN mkdir /opt/app
RUN cd /opt/app

COPY docker-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java","-jar","/opt/app/docker-0.0.1-SNAPSHOT.jar"]

CMD ["--spring.profiles.active=$ENVIRONMENT"]