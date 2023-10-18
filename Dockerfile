FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y

COPY . .

RUN apt-get install maven -y
RUN mvn clean install

FROM openjdk:17-jdk-slim
EXPOSE 8080 

<<<<<<< HEAD
COPY --from=build /target/todolist-1.0.0.jar app.jar
=======
COPY --from=build /target/lodolist-1.0.0.jar app.jar
>>>>>>> c73398cde129cc35a8d593ee09b3b56bbc940201

ENTRYPOINT [ "java", "-jar", "app.jar" ]