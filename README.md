![pipeline](https://gitlab.com/todolist-micro-services/todolist-authentification-api/badges/master/pipeline.svg)

# todolist-authentification-api

API to handle login and register

Java = 21

Gradle = 8.5.0

[API documentation](https://area-api.postman.co/workspace/Pad'workplace~c06a04b9-d1ce-4a4d-8dc0-20c453ca7fae/api/ce31f1a3-1513-4d82-8868-239cb39c227a?action=share&creator=15037258)

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
