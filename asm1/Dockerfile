# Bước 1: Build file JAR bằng Maven
FROM maven:3.8.5-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Bước 2: Chạy ứng dụng bằng Java (Sửa lỗi "not found" ở đây)
FROM eclipse-temurin:17-jre-jammy
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]