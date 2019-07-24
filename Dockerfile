FROM java:8

WORKDIR / 

COPY ./target java/home

EXPOSE 8081

CMD java - jar java/home/demo-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "java/home/demo-0.0.1-SNAPSHOT.jar"]
