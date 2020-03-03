FROM adoptopenjdk/openjdk11-openj9:jdk-11.0.1.13-alpine-slim
COPY target/consumption-service-1.0.jar  consumption-service-1.0.jar
EXPOSE 8080
CMD [ "java","-jar","consumption-service-1.0.jar"]