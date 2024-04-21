# ベースとなるDockerイメージを指定します
FROM openjdk:17-jdk-alpine

# アプリケーションのJARファイルをコピーします
COPY target/doma-0.0.1-SNAPSHOT.jar /app/doma-0.0.1-SNAPSHOT.jar

# アプリケーションのポートを公開します（必要に応じて）
EXPOSE 8080

# アプリケーションを実行します
CMD ["java", "-jar", "/app/doma-0.0.1-SNAPSHOT.jar"]
