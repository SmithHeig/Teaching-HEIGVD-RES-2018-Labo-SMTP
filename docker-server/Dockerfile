#Docker server Java
FROM java:openjdk-8-alpine

ADD src /opt/src/

WORKDIR /opt/src/

ENTRYPOINT ["java","-jar","MockMock-1.4.0.one-jar.jar","-p","2525"]
