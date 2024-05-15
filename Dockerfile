FROM openjdk:17-jdk-slim
# копируем из таргета в контейнер файл с раширением джар и переименовываем его:
COPY target/*.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]