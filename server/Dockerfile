FROM openjdk:11-jre
WORKDIR /opt
COPY build/libs/*.jar .
ENTRYPOINT exec java -Xms100M -Xmx800M -Dspring.profiles.active=$PROFILE -jar *.jar
