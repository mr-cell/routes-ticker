FROM gradle:5.6.2-jdk8 AS build
VOLUME /tmp
COPY src /usr/src/app/src
COPY build.gradle /usr/src/app/build.gradle
COPY settings.gradle /usr/src/app/settings.gradle
WORKDIR /usr/src/app/
RUN gradle build --no-daemon --info

FROM openjdk:8-jre-alpine
WORKDIR /work/
COPY --from=build /usr/src/app/build/libs/config-service-*.jar /work/app.jar
RUN chmod 775 /work
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=native,docker", "app.jar"]