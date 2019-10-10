FROM gradle:5.6.2-jdk8 AS build
VOLUME /tmp
COPY src /usr/src/app/src
COPY build.gradle.kts /usr/src/app/build.gradle.kts
COPY settings.gradle.kts /usr/src/app/settings.gradle.kts
WORKDIR /usr/src/app/
RUN gradle build --no-daemon --info

FROM openjdk:8-jre-alpine
WORKDIR /work/
COPY --from=build /usr/src/app/build/libs/climbing-routes-service-*.jar /work/app.jar
RUN chmod 775 /work
EXPOSE 8765
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "app.jar"]