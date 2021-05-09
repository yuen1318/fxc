FROM openjdk:8
ADD target/fx-api.jar fx-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "fx-api.jar"]