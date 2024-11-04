# 使用Maven官方提供的基于Ubuntu的镜像作为基础镜像
FROM maven:3.8-openjdk-17-slim

# 工作目录设置为/usr/src/app
WORKDIR /app

# 复制项目的pom.xml和源代码到容器中
COPY pom.xml .
COPY src ./src

# 运行Maven打包命令
RUN mvn package -DskipTests

# 设置容器启动时执行的命令为运行打包后的应用
CMD ["java", "-jar", "/app/target/user-center-0.0.1-SNAPSHOT.jar","--spring.profiles.active=prod"]