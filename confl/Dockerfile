# Этап сборки
FROM gradle:8.6-jdk21-alpine AS builder

# Установка рабочей директории
WORKDIR /app

# Копируем только необходимые для сборки файлы
COPY settings.gradle .
COPY confl/build.gradle .
COPY confl/build.gradle ./confl/
COPY confl/src ./confl/src/

# Сборка проекта (используем правильное имя модуля из settings.gradle)
RUN gradle :confl:build --no-daemon -x test

# Этап выполнения
FROM eclipse-temurin:21-jre-jammy

# Копирование собранного JAR-файла
COPY --from=builder /app/confl/build/libs/confl-*.jar /app/confl.jar

# Команда запуска
CMD ["java", "-jar", "/app/confl.jar"]