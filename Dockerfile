FROM openjdk:11.0.4
MAINTAINER Sergii Maliarov <sergii.maliarov@gmail.com>

ARG JAR_FILE

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/lib/payout.jar"]
VOLUME /tmp
EXPOSE 8080

COPY target/${JAR_FILE} /app/lib/payout.jar