FROM ghcr.io/doda25-team15/app:latest

WORKDIR /app

ENV MODEL_HOST="http://localhost:8081"
ENV PORT=8080

EXPOSE ${PORT}

CMD ["java", "-jar", "app.jar"]