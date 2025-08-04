# Этап сборки
FROM gradle:8.6-jdk21-alpine AS builder

# Установка рабочей директории
WORKDIR /app

# Копирование всех файлов проекта
COPY --chown=gradle:gradle . .

# Сборка проекта (с именем модуля :user)
RUN gradle :user:build --no-daemon -x test

# Этап выполнения
FROM eclipse-temurin:21-jre-jammy

# Копирование собранного JAR-файла
COPY --from=builder /app/user/build/libs/user-*.jar /app/user.jar

# Команда запуска
CMD ["java", "-jar", "/app/user.jar"]