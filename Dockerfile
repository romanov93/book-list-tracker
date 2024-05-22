FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /
COPY /src /src
COPY checkstyle-suppressions.xml /
COPY checkstyle-configuration.xml /
COPY pom.xml /
RUN mvn -f /pom.xml clean package

FROM openjdk:17-jdk-slim
# копируем из таргета в контейнер файл с раширением джар и переименовываем его:
COPY --from=build target/*.jar application.jar
# внутренний порт контейнера
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]