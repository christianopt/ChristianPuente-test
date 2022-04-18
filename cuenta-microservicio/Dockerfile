FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/cuenta-microservicio-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} cuenta-microservicio.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","cuenta-microservicio.jar"]