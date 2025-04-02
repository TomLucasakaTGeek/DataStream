FROM openjdk : 11
COPY target/data-service.jar /app.jar
CMD ["java", "-jar", "/app.jar"]