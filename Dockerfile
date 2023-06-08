FROM gradle:8.1.1-jdk11-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:11
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/ReceiptProcessorApp-1.0-SNAPSHOT.jar /app/receipt-processor.jar
ENTRYPOINT ["java", "-jar","/app/receipt-processor.jar"]