FROM openjdk:11-jdk-oracle
VOLUME /usr/local/
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]