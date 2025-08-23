# ---- Build stage ----
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom first
COPY email_sender/pom.xml email_sender/pom.xml
RUN mvn -f email_sender/pom.xml -q -e -DskipTests dependency:go-offline

# Copy source and build
COPY email_sender ./email_sender
RUN mvn -f email_sender/pom.xml -q -DskipTests package

# ---- Runtime stage ----
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the fat jar from build stage
COPY --from=build /app/email_sender/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
