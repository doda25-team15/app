FROM eclipse-temurin:25-jdk AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:25-jdk
WORKDIR /app

ENV MODEL_HOST="http://localhost:8081"

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]