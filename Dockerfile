FROM openjdk:11.0.4
MAINTAINER Sergii Maliarov <sergii.maliarov@gmail.com>

ARG JAR_FILE

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/lib/payout.jar"]
VOLUME /tmp
EXPOSE 8080

# Add libs
COPY target/lib /app/lib
# Add the service itself
COPY target/${JAR_FILE} /app/lib/payout.jar