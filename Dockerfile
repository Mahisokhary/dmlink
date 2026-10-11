FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /dmlink-build

COPY pom.xml ./
COPY dmlink-domain/pom.xml dmlink-domain/
COPY dmlink-application/pom.xml dmlink-application/
COPY dmlink-spring/pom.xml dmlink-spring/
COPY dmlink-spring/dmlink-spring-infrastructure/pom.xml dmlink-spring/dmlink-spring-infrastructure/
COPY dmlink-spring/dmlink-spring-presentation/pom.xml dmlink-spring/dmlink-spring-presentation/
COPY dmlink-spring/dmlink-spring-bootstrap/pom.xml dmlink-spring/dmlink-spring-bootstrap/
RUN mvn -B -P '!development,!database-h2,database-postgresql' -pl dmlink-spring/dmlink-spring-bootstrap -am dependency:go-offline -Pprod

COPY . .
RUN mvn -B -P '!development,!database-h2,database-postgresql' -pl dmlink-spring/dmlink-spring-bootstrap -am clean package

FROM tomcat:10-jdk17 AS runtime
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /dmlink-build/dmlink-spring/dmlink-spring-bootstrap/target/dmlink-spring-bootstrap-1.0.0.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
