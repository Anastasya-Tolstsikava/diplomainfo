FROM anapsix/alpine-java
MAINTAINER Anastasya_Tolstsikava
COPY testprj-1.0-SNAPSHOT.jar /home/testprj-1.0-SNAPSHOT.jar
CMD ["java","-jar","/home/testprj-1.0-SNAPSHOT.jar"]