FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY ./build/libs/account-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

CMD ["java", "-jar", "app.jar"]