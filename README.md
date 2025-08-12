создание пользователя
Сервис user принимает запрос на http://localhost:8080/api/users с телом "name": "User Name", "email": "user@examle.com"
Сервис user берет поле email и отравляет запрос сервису confl на http://localhost:8081/api/email
Сервис confl ищет в базе данных confldb данный email
Если в базе confldb данный email не найден, сервис confl передает сервису user "available" и создает запись в confldb с полями id и email
Сервис user создает запись в базе userdb
альтернативный сценарий
Если в базе confldb данный email найден, сервис confl передает сервису user badRequest().body("Такой email уже зарегистрирован")
Сервис user транслирует ответ от сервиса confl
![photo_2025-07-07_15-23-10](https://github.com/user-attachments/assets/822f538f-773e-40c2-8397-dfeef3e3d090)
