## Приложение для оправки прогноз погоды на email

---

### Использование:
curl --location --request POST 'localhost:8080/send-pdf' \
--header 'Content-Type: application/json' \
--data-raw '{
"city": "Комсомольск-на-Амуре",
"email": "456kot@mail.ru"
}'

### Обязательные свойства
#### Api ключ от https://openweathermap.org/
- openweathermap.api.key
#### Настройки соединения с email сервером
- mail.host
- mail.port
- mail.username
- mail.password
- mail.properties.mail.transport.protocol
- mail.properties.mail.smtp.auth
- mail.properties.mail.smtp.starttls.enable
#### Ваша почта как отправителя
- mail.from


---
Done: 15 hours