FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY ./build/libs/config-0.0.1-SNAPSHOT.jar app.jar
# Config repo will be mounted as volume from docker-compose

EXPOSE 9999

CMD ["java", "-jar", "app.jar"]