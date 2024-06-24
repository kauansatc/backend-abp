FROM maven:3.8.1-openjdk-17-slim AS build

RUN mkdir -p /workspace

WORKDIR /workspace

COPY ./pom.xml ./pom.xml

RUN mvn dependency:go-offline -B

COPY ./src ./src

RUN mvn clean package

FROM openjdk:17

EXPOSE 8080

COPY --from=build /workspace/target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]