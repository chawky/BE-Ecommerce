# Stage 1: Build the application
FROM openjdk:17-jdk-alpine as build

WORKDIR /app

# Copy maven wrapper and .mvn directory for offline builds
COPY mvnw .
COPY .mvn .mvn

# Copy the pom.xml file
COPY pom.xml .

# Download dependencies for offline use
RUN ./mvnw dependency:go-offline -B

# Copy the source code
COPY src src

# Build the application
RUN ./mvnw package -DskipTests

# Prepare the application for running
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# Stage 2: Create minimal runtime image
FROM openjdk:17-jdk-alpine

ARG DEPENDENCY=/app/target/dependency

# Copy dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

# Set the entry point to run the application
ENTRYPOINT ["java", "-cp", "app:app/lib/*", "Stream.project.stream.StreamApplication"]
