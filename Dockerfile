#
# Build
#

FROM maven:3.6.3-openjdk-8 AS build

COPY src /home/app/src

COPY pom.xml /home/app

RUN mvn -f /home/app/pom.xml clean package -DskipTests

#
# Package
#

FROM openjdk:8-jdk-alpine

COPY --from=build /home/app/target/assembleia-api-0.0.1-SNAPSHOT.jar /usr/local/lib/assembleia.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","/usr/local/lib/assembleia.jar"]