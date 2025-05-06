# 使用一个基础的 Java 镜像
FROM openjdk:21-jdk-slim

# 设置工作目录
WORKDIR /app

# 将打包好的 JAR 文件复制到容器中
COPY target/userTransactionRecords.jar userTransactionRecords.jar

# 暴露应用程序端口，需根据实际情况修改
EXPOSE 8080

# 运行 Spring Boot 应用程序
CMD ["java", "-jar", "userTransactionRecords.jar"]