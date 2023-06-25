***
#### (временно)
# TechBazzar
Данные проекта: <br>
Ссылка на [репозиторий][repo]   
Гугл [документ][doc] проекта, ТЗ.

[repo]: https://github.com/SergeiMarkushov/TechBazzar
[doc]: https://docs.google.com/document/d/1r1NcJigLT8cSW7z1JUSfOGne5YAHIotE77O1uCUTVQQ
#### (временно)
***

# Проект
## Интернет-магазин "TechBazzar"
***
![Logo](TechBazzar.png)
***
## Цель проекта
- [x] Применить свои навыки на практике
- [x] Узнать что-то новое и внедрить в проект
- [x] Разработать сервис “Интернет магазин"
- [x] Сервис позволяет осуществлять деятельность интернет-магазина.
- [ ] Сервис должен быть доступен в браузере через интернет

## UserStory
### В магазине должны быть реализованы следующие правила:

- [ ] Функционал админа расширяет весь упомянутый функционал пользователя;
- [ ] Админ может добавлять и изменять любую информацию о товарах в магазине;
- [ ] Информация о товаре состоит из:      
  -    Названия;
  -    Описания;
  -    Организации;
  -    Цены;
  -    Количества на складе;
  -    Информации о скидках;
  -    Отзывов;
  -    Ключевых слов;
  -    Таблицы характеристик;
  -    Оценок.

- [ ] Для товара или группы товаров админ может добавлять и изменять скидки;
- [ ] Информация о скидке состоит из:
  -  Задействованных товаров;
  -  Объема скидки;
  -  Длительности скидки.

- [ ] Для того чтобы воспользоваться магазином, пользователь должен быть зарегистрированным и войти в учетную запись;
- [ ] Пользователь может покупать товары, оставлять о них отзывы и ставить оценки;
- [ ] Каждая покупка должна сохраняться в истории покупок;
- [ ] Пользователь может просмотреть свою историю покупок;
- [ ] Админ может просмотреть историю покупок любого пользователя;
- [ ] Пользователь может совершить возврат в течении суток с момента покупки;
- [ ] Пользователь не может оценить или оставить отзыв о товаре, не купив его;
- [ ] Информация о пользователе состоит из:
  -  Юзернейма;
  -  Почты;
  -  Пароля;
  -  Баланса.
  
- [ ] Админ может пополнять баланс пользователя;
- [ ] Админ может просматривать информацию о пользователях, удалять и замораживать их аккаунты;
- [ ] Админ может отправлять пользователям уведомления
- [ ] После модерации организаций и продуктов отправлять уведомление владельцам;
  -  Уведомления состоят из:
  -  Заголовка уведомления;
  -  Даты уведомления;
  -  Текстового содержания уведомления.
  
- [ ] Пользователь может просмотреть свои уведомления
- [ ] Пользователь может подать заявку на регистрацию организации;
- [ ] Организация дает возможность ее создателю добавлять товары и продавать их в данном магазине;   доработать(только собственник может добавлять продукты)
- [ ] Организация состоит из:
  - Имени;
  - Описания
  - Логотипа;
  - Товаров.

- [ ] Так как заявка на регистрацию организации добавляется авторизованным пользователем, то получателем выручки является данный пользователь;
- [ ] Каждый пользователь может создавать неограниченное количество организаций;
- [ ] Пользователи, зарегистрировавшие свою организацию, могут добавлять заявки на регистрацию товаров, и после модерации будут добавлены в общий список товаров; доработать (только собственник может добавлять продукты )
- [ ] При добавлении товара пользователем, он обязан указать, от лица какой организации этот товар поставляется;
- [ ] Выручку с покупки товаров, принадлежащих организации, за вычетом комиссии, получает организация. Комиссия произвольная (например, 5%);
- [ ] Админ вправе принимать заявки на регистрацию организации, замораживать и удалять активные организации
- [ ] Если организация заморожена или удалена, пользователи не должны видеть товары в списке доступных, однако, у купленных товаров должна сохраняться информация об организации. Т.е. даже об удаленных, а точнее забаненных организациях информация должна оставаться;
- [ ] …
- [ ] …
- [ ] …(на редакции)

### Идеологически магазин реализует подход Kanban
***
# Команда
| ФИ               | Роль                        | Контакт                    |
|------------------|-----------------------------|----------------------------|
| Бородинов Юрий   | бэк, youGile manager        | Telegram - @Aditon         |
| Бехтер Николай   | бэк, ведущий проекта        | Telegram - @N_Bekhter      |
| Шеховцов Сергей  | бэк, технологии             | Telegram - @sergey_shekhov |
| Маркушов Сергей  | бэк, GitHub manager, дизайн | Telegram - @Serega_Markus  |
| Федоренко Вадим  | фронт, бэк, дизайн          | Telegram - @KekNyaV        |
***
# Архитектура(предварительно)

***
## Решение представляет собой набор из сервисов:

- [ ] auth-service
- [ ] cart-service
- [ ] core-service
- [ ] front-service
- [ ] gateway-service
- [ ] notification-service
- [ ] organization-service
- [ ] picture-service
- [ ] keycloak-service
***

# Технологии
## В проекте используются следующие технологии(на начальном этапе):

- [ ] Java 17
- [ ] Spring boot 2.5.14
- [ ] spring-boot-starter-mail
- [ ] spring-boot-devtools
- [ ] Spring Cloud Gateway
- [ ] jakarta.xml.bind-api
- [ ] jaxb-api
- [ ] JPA
- [ ] webflux
- [ ] Flyway
- [ ] jjwt
- [ ] lombok
- [ ] Swagger
- [ ] Docker
- [ ] Postgresql
- [ ] maven
- [ ] React.js
- [ ] Axios
- [ ] Formik
- [ ] TypeScript
***
# Данные проекта
### Ссылка на [репозиторий][repo]    
### Ссылка на [доску задач][desc] команды 

[repo]: https://github.com/SergeiMarkushov/TechBazzar "репозиторий"
[desc]: https://yougile.com/board/izg2ndlwgd55 "доска задач"
***
# Запуск проекта

***
***

## License

[![CC0](https://licensebuttons.net/p/zero/1.0/88x31.png)](https://creativecommons.org/publicdomain/zero/1.0/)



