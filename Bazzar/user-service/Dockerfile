#FROM adoptopenjdk:11-jdk-hotspot AS builder
#FROM maven:3.8.3-openjdk-17-slim AS build
#COPY . /app
#WORKDIR /app
#RUN mvn package
#FROM openjdk:17.0.1-jdk-slim
#COPY --from=build /app/target/*.jar /app/app.jar
#EXPOSE 8181
#ENTRYPOINT ["java", "-jar", "/app/app.jar"]
FROM adoptopenjdk:11-jdk-hotspot AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests

FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/app.jar
EXPOSE 8181
ENTRYPOINT ["java", "-jar", "/app/app.jar"]