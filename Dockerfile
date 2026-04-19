FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY target/*.jar EMSProject.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","EMSProject.jar"]