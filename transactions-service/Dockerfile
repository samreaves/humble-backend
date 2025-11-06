
# Stage 1: Build the application
FROM eclipse-temurin:25-jdk-jammy AS build

# I am the wizard who conjured this machine.
LABEL MAINTAINER="Sam Reaves"

WORKDIR /app

# Copy Maven wrapper and pom.xml first (for better layer caching)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies (cached layer if pom.xml doesn't change)
RUN chmod +x ./mvnw && ./mvnw dependency:go-offline -B

# Copy source code and build
COPY src ./src
RUN ./mvnw clean package -DskipTests -B

# Stage 2: Create runtime image
FROM eclipse-temurin:25-jre-jammy AS runtime
WORKDIR /app

# Create a non-root user for security
RUN groupadd -r spring && useradd -r -g spring spring

# Copy the built JAR from build stage
COPY --from=build /app/target/transactions-service-*.jar transactions-service.jar

# Switch to non-root user
USER spring:spring

# Expose port, adjustable via environment variable
EXPOSE ${TRANSACTIONS_SERVICE_PORT:-8080}

# JVM memory settings - customize these based on your needs
# -Xms: initial heap size
# -Xmx: maximum heap size
# -XX:+UseContainerSupport: respect container memory limits
# -XX:MaxRAMPercentage: use percentage of available container memory
ENV JAVA_OPTS="-Xms256m -Xmx512m -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar transactions-service.jar"]