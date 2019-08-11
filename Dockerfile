FROM openjdk:12-jdk-alpine
MAINTAINER Sergii Maliarov <sergii.maliarov@gmail.com>

VOLUME /tmp
# Add Maven dependencies (not shaded into the artifact; Docker-cached)
#ADD target/lib           /app/lib
# Add the service itself
ARG JAR_FILE
ADD target/${JAR_FILE} /app/lib
ENTRYPOINT ["java","-cp","/app/lib/*","com.maliarov.payaut.PayautApplication"]