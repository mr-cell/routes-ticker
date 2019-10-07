FROM openjdk:8-jdk-alpine AS build
VOLUME /tmp
COPY src /usr/src/app/src
COPY build.gradle /usr/src/app/build.gradle
COPY settings.gradle /usr/src/app/settings.gradle
COPY gradlew /usr/src/app/gradlew
RUN chmod +x /usr/src/app/gradlew
COPY gradle /usr/src/app/gradle
WORKDIR /usr/src/app/
RUN ./gradlew build

FROM openjdk:8-jdk-alpine
WORKDIR /work/
COPY --from=build /usr/src/app/build/libs/eureka-service-*.jar /work/app.jar
RUN chmod 775 /work
EXPOSE 8765
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "app.jar"]