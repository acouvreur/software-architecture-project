FROM openjdk:8-jre-alpine
ADD target/matching-1.0-SNAPSHOT.jar /matching.jar

# Healthcheck
RUN apk add --no-cache curl
HEALTHCHECK CMD curl --fail http://localhost:8080/ || exit 1

CMD java -jar /matching.jar