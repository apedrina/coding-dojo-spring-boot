FROM openjdk:11 AS coding-dojo

ADD ./target/coding-dojo.jar /app/
EXPOSE 8102

CMD ["java", "-jar", "-Dspring.profiles.active=prod", "/app/coding-dojo.jar"]