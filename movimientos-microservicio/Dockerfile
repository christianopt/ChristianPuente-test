FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/movimientos-microservicio-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} movimientos-microservicio.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar","movimientos-microservicio.jar"]
