
FROM openjdk:17-jdk-slim AS build
WORKDIR /app

COPY pom.xml .
RUN apt-get update && apt-get install -y maven && mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]