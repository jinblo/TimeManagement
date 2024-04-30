FROM maven:3.8.5-openjdk-17
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY . /app
RUN mvn clean
RUN mvn package -DskipTests
EXPOSE 8080
ENTRYPOINT [ "java","-jar","target/TimeManagementBE-0.0.1-SNAPSHOT.jar" ]
CMD ["java", "-jar", "TimeManagementBE-0.0.1-SNAPSHOT.jar"]