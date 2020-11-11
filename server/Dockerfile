FROM openjdk:11-jre
WORKDIR /opt
COPY build/libs/*.jar .
ENTRYPOINT exec java -Dspring.profiles.active=$PROFILE -jar *.jar
