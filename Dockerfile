FROM --platform=linux/amd64 gradle:8.5.0-jdk21-alpine

WORKDIR todolist-api

COPY . .

COPY .env .env

RUN ./gradlew build

ENTRYPOINT ["./gradlew","bootRun"]
