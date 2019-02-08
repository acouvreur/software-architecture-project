FROM openjdk:8-jre-alpine
ADD target/announcement-1.0-SNAPSHOT.jar /announcement.jar

# Healthcheck
RUN apk add --no-cache curl
HEALTHCHECK CMD curl --fail http://localhost:8080/ || exit 1

CMD java -jar /announcement.jar