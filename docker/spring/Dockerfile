#build
FROM openjdk:19 as builder
WORKDIR /linetime
COPY backend .
RUN ./mvnw clean package -DskipTests

#run
FROM openjdk:19
COPY --from=builder /linetime/target/linetime-backend.jar linetime-backend.jar
ENTRYPOINT ["java", "-jar", "linetime-backend.jar"]
EXPOSE 8080
