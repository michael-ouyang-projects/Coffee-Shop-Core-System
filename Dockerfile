FROM openjdk:8-jdk-alpine
WORKDIR /usr/src/app
COPY target/rscs-1.0.jar rscs.jar
ENTRYPOINT ["java","-jar","rscs.jar"]
