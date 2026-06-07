#orchestrator
# Build stage
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy shared modules
COPY utilities/ utilities/
COPY orchestrator/ orchestrator/


COPY pomcreate.sh .

RUN chmod +x ./pomcreate.sh

RUN ./pomcreate.sh

RUN mvn -pl orchestrator -am clean package -DskipTests


FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/orchestrator/target/*.jar app.jar

EXPOSE 5001

ENTRYPOINT ["java", "-jar", "app.jar"]