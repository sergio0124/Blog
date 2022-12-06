# Блог #
Программный продукт предназначен для хранения информации о постах, комментариях, лайках, пользователях, банах.
в дальнейшем можно релизовывать дополнительные функции, такие как рекомендации, тэги.
Тему выбрал потому, что много всего можно сделать в рамках предметной области, сделаю проект петкой.


[Видео](https://drive.google.com/file/d/1PeNGCCgdbsuu8jyG8IH1cn6Jy5WmS4Gd/view?usp=share_link) с демонстрацией функционала *Возможности юзеров можно скипнуть

З.Ы. Первые 4 дня я потратил на устранения багов, вызванных особенностями Spring (душещипательная история, мб потом расскажу),
а потом разработка шла хаотично, поэтому на коммиты не ругайтесь, обычно они информативнее и содержательнее
<br/>

## По заданиям ##

1. Разработать свой веб-сервис, который оказывает онлайн 
услуги клиенту с использованием REST-запросов (есть).

2. Сервис должен предоставлять возможность выполнения операций согласно 
ролям пользователей (минимум 2 роли) ------- полно реализованы роли Блоггера (CREATOR) и (USER). Роли админа и главного админа
не реализованы, но у них не такой широкий функционал.

3. Сервис должен обрабатывать данные в БД с использованием HIbernate (минимум 3 сущности со связями)
-------- Лучше всего смотреть работу с Post и User сущностями (там еще лайки, комменты и картинки в постах, но с ними работа специфическая) ([service](src/main/java/reznikov/sergey/blog/services), [entities](src/main/java/reznikov/sergey/blog/entities), [repositories](src/main/java/reznikov/sergey/blog/repositories))

4. Сервис должен предоставлять CRUD операции через REST -------
Лучше всего операции прописаны в контроллерах: [user](src/main/java/reznikov/sergey/blog/controllers/UserController.java), [creator](src/main/java/reznikov/sergey/blog/controllers/CreatorController.java)

5. Сервис должен уметь обрабатывать фоновые процессы (Schedule) -------
У меня не было задачек, которые можно через schedule сделать. Позднее
планирую прикрутить восстановление пароля по почте и авторизацию с хранением хэшей
в памяти так, чтобы они очищались по времени. Пока сделал логирование по таймауту
   [ScheduleConfig](src/main/java/reznikov/sergey/blog/configurations/ScheduleConfig.java), [ScheduledService](src/main/java/reznikov/sergey/blog/services/ScheduledService.java)

* Сервис должен интегрироваться со сторонними сервисами с использованием 
RestTemplate или FeignClient ----- Сделал почту [config](src/main/java/reznikov/sergey/blog/configurations/MailConfig.java), [sevice](src/main/java/reznikov/sergey/blog/services/MailSender.java) 

* Сервис должен иметь UI-часть ------ *Здесь большая часть времени * Я написал тысячу [js](src/main/resources/static/user/comment.js) скриптов, css не завез (см. видео или [templates](src/main/resources/templates/user))

* Использовать MapStruct ------ использовал фреймворк [modelmapper](https://mvnrepository.com/artifact/org.modelmapper/modelmapper).
Реализация в [mappings](src/main/java/reznikov/sergey/blog/mappings), [DTO](src/main/java/reznikov/sergey/blog/DTO)

* Подключить swagger ------- Я его не использовал, т.к. все через UI сразу делал, реализация: [swagger bean](src/main/java/reznikov/sergey/blog/configurations/MvcConfig.java). 

* Использовать unit-тесты ------- Тестирование ручное было, пример здесь: [registration test](src/test/java/reznikov/sergey/blog/controllers/AuthControllerTest.java)

* Использовать AOP ------- [логирование](src/main/java/reznikov/sergey/blog/aspect/ServiceLogging.java)

## Возможности Создателя ##

* Регистрация, авторизация
* Редактирование пароля, логина
* Создание, редактирование и удаление постов
* В рамках работы с постами можно удалять и добавлять изображения


## Возможности Юзверя ##

* Регистрация, авторизация
* Редактирование пароля и логина
* Просмотр постов по подпискам
* Поиск постов по названиям
* Чтение постов
* Создание, редактирование и удаление комментариев под постами
* Оставление лайков или же их удаление
* Подписка на блогера или же отписка
* Просмотр страницы блогера

## Что еше должно быть здесь сейчас, но появится только потом ##

* Роль админа_админов: создание, редактирование и удаление админов
* Роль админа: рассмотрение репортов, блок пользователей, удаление комментов, постов, восстановление аккаунтов тоже
* У пользователей больше возможностей для поиска и возможность репортить
* Добавить лайки и репорты и комменты к комментам
* Участие почты в аккаунте
* Сбор статистики по постам: посещения, лайки и т.д.

