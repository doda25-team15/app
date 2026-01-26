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

The application depends on `lib-version`, which is published to GitHub Packages.
Accessing GitHub Packages requires authentication.

**Step 1: Create GitHub Personal Access Token (PAT)**

1. Go to [GitHub Settings → Tokens](https://github.com/settings/tokens)
2. Click **"Generate new token (classic)"**
3. Configure token and set the required scope as `read:packages`
4. Click **"Generate token"**
5. **Copy the token** and save it in your system


**Step 2: Configure Maven Credentials**

Create or edit `~/.m2/settings.xml` in your local system:

```xml
<settings>
  <servers>
    <server>
      <id>github-packages</id>
      <username>GITHUB_USERNAME</username>
      <password>GITHUB_TOKEN</password>
    </server>
  </servers>
</settings>
```

Replace `GITHUB_USERNAME` with your Github username and `GITHUB_TOKEN` with the token you created in the previous step.

**Step 3: Compile the application using maven**

```shell
./mvnw clean package -DskipTests
```

**Step 4: Build docker image**

```shell
docker buildx build \
  --platform linux/amd64,linux/arm64 \
  --secret id=github_token,env=GITHUB_TOKEN \
  -t spring-app .
```

**Step 5: Start docker container**

```shell
docker run \
-e PORT=8080 \
-e MODEL_HOST="http://host.docker.internal:8081" \
-p 8080:8080 \
spring-app
```

The port for the app can be changed by modifying the `PORT` environment variable and the `-p` argument accordingly. The `MODEL_HOST` variable should point to where the backend is running.
