![pipeline](https://gitlab.com/todolist-micro-services/todolist-authentification-api/badges/master/pipeline.svg)

# todolist-authentification-api

API to handle login and register

Java = 21

Gradle = 8.5.0

## build

```bash
./gradlew build # --info

gradle build
```

## config

add ```application.properties``` in ```src/main/resources``` with this content:

```properties
SERVER_PORT=
DB_URL=
DB_USERNAME=
DB_PASSWORD=
```

## run

```bash
./gradlew bootRun # -> curl localhost:8080
```

## test

```bash
./gradlew test # --info
```
