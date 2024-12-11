FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/*.jar customer-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/customer-service.jar"]
