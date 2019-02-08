FROM openjdk:8-jre-alpine
ADD target/tracking-1.0-SNAPSHOT.jar /tracking.jar

# Healthcheck
RUN apk add --no-cache curl
HEALTHCHECK CMD curl --fail http://localhost:8080/ || exit 1

CMD java -jar /tracking.jar