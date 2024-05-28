# Используем официальный образ Maven для сборки
FROM maven:3.9-amazoncorretto-17 AS build

# Указываем рабочую директорию внутри контейнера
WORKDIR /app

# Скопируем pom.xml и скачиваем зависимости
COPY pom.xml .
RUN mvn dependency:go-offline

# Скопируем исходный код и собираем проект
COPY src ./src
RUN mvn clean package

# Второй этап, создаем легковесный образ для запуска
FROM amazoncorretto:17

# Указываем рабочую директорию
WORKDIR /app

# Копируем собранный артефакт из предыдущего этапа
COPY --from=build /app/target/gateway.jar ./gateway.jar

# Команда запуска приложения
ENV SERVER_PORT 8080
CMD ["java", "-jar", "gateway.jar", "--server.port=${SERVER_PORT}"]