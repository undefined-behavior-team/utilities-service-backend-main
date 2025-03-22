# Используем OpenJDK 17 для сборки
FROM eclipse-temurin:17-jdk AS builder

# Создаем рабочую директорию
WORKDIR /build

# Копируем POM-файл и загружаем зависимости
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# Копируем весь проект в контейнер
COPY src ./src

# Собираем проект без тестов
RUN mvn clean package -DskipTests

# Используем JRE 17 для финального контейнера
FROM eclipse-temurin:17-jre

# Создаем рабочую директорию для финального контейнера
WORKDIR /app

# Копируем скомпилированный JAR из предыдущего контейнера
COPY --from=builder /build/target/*.jar ./app.jar

# Даем права на выполнение
RUN chmod 755 ./app.jar

# Открываем порт 8080
EXPOSE 8080

# Запускаем Spring Boot приложение
ENTRYPOINT ["java", "-jar", "app.jar"]