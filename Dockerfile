FROM eclipse-temurin:21
WORKDIR /app
COPY . .
RUN javac -cp "lib/*" -d out src/main/java/**/*.java
CMD ["java", "-cp", "out:lib/*", "main.java.server.AppServer"]