FROM eclipse-temurin:25-jdk
WORKDIR /app

ENV MODEL_HOST="http://localhost:8081"
ENV PORT=8080

COPY target/*.jar app.jar

EXPOSE ${PORT}
ENTRYPOINT ["java", "-jar", "app.jar"]
