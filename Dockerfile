# Используем OpenJDK 21 для сборки
FROM eclipse-temurin:21-jdk AS builder

# Создаем рабочую директорию
WORKDIR /build

# Копируем весь проект в контейнер
COPY . /build/.

RUN chmod +x ./mvnw

# Собираем проект без тестов
RUN ./mvnw clean package -DskipTests

# Используем JRE 21 для финального контейнера
FROM eclipse-temurin:21-jre

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