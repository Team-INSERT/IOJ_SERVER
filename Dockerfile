FROM openjdk:17-slim

USER root

ENV TZ=Asia/Seoul

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

RUN apt update && apt install -y docker.io

ADD executions executions

ENTRYPOINT ["java", "-jar", "app.jar"]
