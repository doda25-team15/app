# SMS Checker / Frontend

The frontend allows users to interact with the model in the backend through a web-based UI.

The frontend is implemented with Spring Boot and only consists of a website and one REST endpoint.
It **requires Java 25+** to run (tested with 25.0.1).
Any classification requests will be delegated to the `backend` service that serves the model.
You must specify the environment variable `MODEL_HOST` to define where the backend is running.

The frontend service can be started through running the `Main` class (e.g., in your IDE) or through Maven (recommended):

```shell
MODEL_HOST="http://localhost:8081" ./mvnw spring-boot:run
```

Or on Windows:

```shell
$env:MODEL_HOST="http://localhost:8081"; ./mvnw spring-boot:run
```

The server runs on port 8080. Once its startup has finished, you can access [localhost:8080/sms](http://localhost:8080/sms) in your browser to interact with the application.

## Setting up locally with Docker

The docker image can be built with:

```shell
docker buildx build \
  --platform linux/amd64,linux/arm64 \
  --secret id=github_token,env=GITHUB_TOKEN \
  -t spring-app \
  .
```

The docker container can be started with:

```shell
docker run \
-e PORT=8080 \
-e MODEL_HOST="http://host.docker.internal:8081" \
-p 8080:8080 \
spring-app
```

The port for the app can be changed by modifying the `PORT` environment variable and the `-p` argument accordingly. The `MODEL_HOST` variable should point to where the backend is running.
