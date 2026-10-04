FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/classes /app/classes

EXPOSE 8081

CMD ["java", "-cp", "/app/classes", "com.devops.App"]