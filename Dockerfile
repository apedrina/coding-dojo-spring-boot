FROM openjdk:11 AS coding-dojo-spring-boot

ADD ./target/coding-dojo.jar /app/

CMD ["java", "-jar", "/app/coding-dojo.jar"]