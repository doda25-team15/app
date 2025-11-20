
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app

RUN apt-get update && apt-get install -y maven

COPY pom.xml .
COPY src ./src

# build app. clean - cleans dir, package - creates artifact, DskipTests - skips test (not sure if it actually skips it)
RUN mvn -q clean package -DskipTests 

FROM eclipse-temurin:25-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENV MODEL_HOST=http://localhost:8081

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]