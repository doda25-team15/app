FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

# build app. clean - cleans dir, package - creates artifact, DskipTests - skips test (not sure if it is needed)
RUN mvn -q clean package -DskipTests 

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENV MODEL_HOST=http://localhost:5000

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]