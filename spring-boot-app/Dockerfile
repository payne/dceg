# Stage 1: Build Spring Boot app
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Create Spring Boot image
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY --from=build /app/target/my-app-0.0.1-SNAPSHOT.jar ./my-application.jar
EXPOSE 8080
CMD ["java", "-jar", "my-application.jar"]

