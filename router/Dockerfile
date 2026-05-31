#router
# Build stage
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy shared modules
COPY utilities/ utilities/
COPY router/ router/


COPY pomcreate.sh .

RUN chmod +x ./pomcreate.sh

RUN ./pomcreate.sh

RUN mvn -pl router -am clean package -DskipTests


FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/router/target/*.jar app.jar

EXPOSE 5005

ENTRYPOINT ["java", "-jar", "app.jar"]