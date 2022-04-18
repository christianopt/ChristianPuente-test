FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/client-microservicio-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} cliente-microservicio.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","cliente-microservicio.jar"]