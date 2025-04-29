# Restaurant

Приложение для управления заказами в ресторане

## Стек:

Java 21\
Maven\
PostgreSQL\
Spring Boot 3.4.3\
Spring-Boot-Web\
Spring-Boot-Data-JPA\
Mybatis\
Project Lombok\
Kafka\
Feign Client\
GRPC

## Описание проекта:

Разработанное приложение предназначено для управления заказами в ресторане.\
Оно состоит из двух микросервисов: waiter-service (сервис официантов) и kitchen-service (сервис кухни).\
Микросервисы взаимождействуют между собой через REST API с помощью Feign Client,
а также по протоколу GRPC и отправляют сообщения в топики Kafka и читают их оттуда.\
Приложение поддреживает основные операции с заказами, такие как создание заказов, управление их статусами,
получение конкретного заказа или списка, а также отклонение заказа и некоторые другие.

## Запуск приложения:

1. Клонируйте репозиторий:
   ```
   git clone https://gitlab.com/ru.liga2/restaurant
   ```

2. Запустите контейнеры докер:\
   cd docker\
   docker-compose up -d

3. Запустите поочереди микросервисы waiter-service и kitchen-service:
    * для waiter-service:
   ```
   cd waiter-service
   mvn spring-boot:run
   ```
    * для kitchen-service:
   ```
   cd ../kitchen-service
   mvn spring-boot:run
   ```
   Обратите внимание, что команды должны быть вызваны из корневой директории проекта в разных терминалах.

4. Откройте браузер и перейдите по адресу:

    * http://localhost:8070/swagger-ui/index.html - для waiter-service
    * http://localhost:8080/swagger-ui/index.html  - для kitchen-service

Готово!\
Через открывшейся swagger можно отправлять запросы приложению.\
В репозитории также можно найти файлы с коллекциями для Postman.