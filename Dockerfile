# Stage 1: Build the Spring Boot application
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy Maven configuration first
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy application source code
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests


# Stage 2: Run the application
FROM eclipse-temurin:21-jre

WORKDIR /app

# Create a non-root user
RUN useradd --system --uid 1001 appuser

# Copy the generated JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Run as a non-root user
USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]