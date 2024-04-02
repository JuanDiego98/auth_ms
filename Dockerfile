FROM openjdk:21
WORKDIR /app
COPY target/ms-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]