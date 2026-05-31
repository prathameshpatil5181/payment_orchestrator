#gateway
# Build stage
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy shared modules
COPY utilities/ utilities/
COPY gateway/ gateway/


COPY pomcreate.sh .

RUN chmod +x ./pomcreate.sh

RUN ./pomcreate.sh

RUN mvn -pl gateway -am clean package -DskipTests


FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/gateway/target/*.jar app.jar

EXPOSE 5003

ENTRYPOINT ["java", "-jar", "app.jar"]