FROM openjdk:21-jdk-slim

ARG JAR_FILE=target/JobHubApi-1.0.0.jar

COPY ${JAR_FILE} JobHubApi.jar

EXPOSE 8080

ENTRYPOINT["java","-jar","jobhubapi.jar"]