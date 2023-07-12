FROM openjdk:17-oracle
VOLUME C:\study_workspace\demo\demo
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]