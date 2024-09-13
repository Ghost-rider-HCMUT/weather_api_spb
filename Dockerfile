FROM openjdk:22-jdk
WORKDIR /app
COPY ./target/spb_api-0.0.1-SNAPSHOT.jar app.jar
CMD ["java","-jar","app.jar"]
