FROM openjdk:17-oracle
EXPOSE 8080
ADD target/cloud-file-service-0.0.1-SNAPSHOT.jar cloud-file-service.jar
ENTRYPOINT ["java","-jar","/cloud-file-service.jar"]