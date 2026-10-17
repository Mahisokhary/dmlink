# DM Link
Make your link as short as DM! \
What does DM stands for? no one knows :)

> This is only an API you sure need frontend to use dmlink \
> Currently no official frontend is made for dmlink

## Features
- Make your links shorter
- Advanced permission management

## Targets
Things I wanted to use in the project
- [x] Clean Architecture
- [x] RESTful API
- [x] JWT (JSON Web Token)
- [x] Database Independency (database can be decided on deployment!)
- [x] Unit tests
- [x] Docker
- [x] Caching
- [x] API versioning

## Build

### Development
How to build dmlink for debugging and development?
```shell
# Just package it :)
mvn package
```
And then serve the war file `dmlink-spring/dmlink-spring-bootstrap/target/dmlink-spring-bootstrap-1.0.0.war` (with spring profile: `dev`)

#### Docker (tomcat image)
```shell
# Example of serving dmlink (with profile dev) on docker with tomcat
docker run --rm \
  -p 8080:8080 \
  -v "$PWD/dmlink-spring/dmlink-spring-bootstrap/target/dmlink-spring-bootstrap-1.0.0.war:/usr/local/tomcat/webapps/ROOT.war:ro" \
  -e SPRING_PROFILES_ACTIVE=dev \
tomcat:10-jre17
```

#### IntelliJ IDEA
Use `debug run` run configuration

### Production
How to build dmlink for production?
```shell
# Package it without development and database-h2 profiles
mvn -P '!development,!database-h2' clean package
```
#### Tomcat
Create a `dmlink.properties` or `dmlink.yaml` configuration at `${catalina.base}/conf/` to configure dmlink
```yaml
spring:
  datasource:
    url: jdbc:postgresql://database.example.com:5432/dmlink
    username: dmlink_service
    password: dmlink_service_password
    driver-class-name: org.postgresql.Driver
dmlink:
  jwt:
    secret: secret-key
```
Add libraries required to connect to the database (if not included in the WAR file) to `${catalina.base}/lib/`
Your `catalina.base` should look like this:
```text
${catalina.base}/
├── conf
│   └── dmlink.yaml // dmlink configurations
├── lib
│   └── postgresql-42.7.5.jar // postgresql library (if you wanna use postgresql as database)
└── webapps
    └── dmlink.war // dmlink war file
```
