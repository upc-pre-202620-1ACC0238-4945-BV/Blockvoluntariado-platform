# Multi-stage Dockerfile for BlockVoluntariado Platform Backend
# Stage 1: Build JAR using Maven and Java 25
FROM eclipse-temurin:25-jdk-noble AS builder
WORKDIR /build

# Copy Maven wrapper and POM
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x ./mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline -B || true

# Copy source code and build executable JAR
COPY src/ src/
RUN ./mvnw clean package -DskipTests -B

# Stage 2: Runtime image using Java 25 JRE
FROM eclipse-temurin:25-jre-noble
WORKDIR /app

# Non-root security user
RUN addgroup --system spring && adduser --system spring --ingroup spring
USER spring:spring

# Copy built JAR from builder stage
COPY --from=builder /build/target/platform-0.0.1-SNAPSHOT.jar app.jar

# Expose default HTTP port
EXPOSE 8080

# Environment defaults
ENV SPRING_PROFILES_ACTIVE=prod
ENV PORT=8080

# Run Spring Boot application
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
