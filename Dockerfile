FROM openjdk:11

LABEL description="Flight Reservation Service"

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]