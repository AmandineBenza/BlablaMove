FROM openjdk:8-jdk-alpine


RUN mkdir -p src/main
COPY src/main src/main

VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=target/bbm-2.0-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} bbm.jar

# Run the jar file 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/bbm.jar"]