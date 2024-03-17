FROM gradle:8.6.0-jdk21-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:23

EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/LetovoOS-0.0.1-SNAPSHOT.jar /app/spring-boot-application.jar

ENTRYPOINT ["java", "-jar", "/app/spring-boot-application.jar"]