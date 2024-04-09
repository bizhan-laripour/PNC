FROM maven:latest AS build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME
RUN mvn  clean package install

FROM openjdk:17-oracle
ARG JAR_FILE=/usr/app/target/*.jar

COPY --from=build $JAR_FILE /app/pns.jar
EXPOSE 8800
ENTRYPOINT java -jar /app/pns.jar