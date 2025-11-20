FROM eclipse-temurin:25-jdk AS build
WORKDIR /app

COPY gradlew .
COPY gradle ./gradle
COPY build.gradle gradle.properties ./
COPY src ./src

RUN chmod +x ./gradlew

ARG GITHUB_ACTOR
ARG GITHUB_TOKEN
ENV GITHUB_ACTOR=${GITHUB_ACTOR}
ENV GITHUB_TOKEN=${GITHUB_TOKEN}

ENV MODEL_HOST="http://localhost:8081"
RUN ./gradlew clean build -x test --no-daemon


FROM eclipse-temurin:25-jdk
WORKDIR /app

ENV MODEL_HOST="http://localhost:8081"

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]