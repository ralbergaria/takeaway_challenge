FROM gradle:7.6.1-jdk17 AS buildImage
WORKDIR /app
COPY . .
RUN ./gradlew build --stacktrace -x test

FROM openjdk:17-jdk-slim
VOLUME /tmp
WORKDIR /app
EXPOSE 8080:8080
COPY --from=buildImage /app/build/libs/*.jar demo.jar
ENTRYPOINT ["java","-Xms128M","-Xmx512M","-XX:+ExitOnOutOfMemoryError","-Duser.timezone=Europe/Berlin","-jar","demo.jar"]
