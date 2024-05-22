FROM openjdk:21-slim

LABEL authors="sercheo87"

ENV JAVA_OPTS="-XX:MaxRAMPercentage=75.0 -XX:InitialRAMPercentage=50.0 -XX:MinRAMPercentage=50.0"

RUN addgroup --system appmicro && adduser --system appmicro --ingroup appmicro

USER appmicro

WORKDIR /app

COPY "build/libs/api-cloud-bank-movements-0.0.1-SNAPSHOT.jar" "/app/api-cloud-bank-movements-0.0.1-SNAPSHOT.jar"

ENTRYPOINT exec java ${JAVA_OPTS} -jar api-cloud-bank-movements-0.0.1-SNAPSHOT.jar
