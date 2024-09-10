FROM openjdk:17.0.2-jdk-slim-buster
COPY target/*.jar edu-tracker-student-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "edu-tracker-student-0.0.1-SNAPSHOT.jar"]