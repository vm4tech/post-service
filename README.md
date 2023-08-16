# Подготовка и запуск
- Java 17
- PSQL
- Spring Boot 3.1.2
  - Web
  - Validation
  - Data JPA
  - Lombok
### Пользователя доступа к БД:
`CREATE USER skyeng WITH SUPERUSER PASSWORD 'skyengtest';`

### Создайте базу данных PSQL:
`CREATE DATABASE skyeng_mailing;`

### Если ставите на новую БД, то раскомментируйте данные строчки в ресурсах:
```yaml
spring:
  ...
  jpa:
    ...
    defer-datasource-initialization: true
  sql:
    init:
      mode: always
```
и после 1 запуска закомментируйте их (т.к. они генерируют данные) .
## !!! Если запускаете через генерацию, то первые 2 создания отделений могут быть ошибками из-за автоматической генерации ID!
#### А если не хотите генерировать изначальные данные, то воспользуйтесь API для создания отделений.

### Запуск
Перейдите в каталог приложения и запустите
```shell
./mvnw spring-boot:run
```
сервер запустится на порту `8082` и будет доступен по адресу: http://localhost:8082/api/v1
________________________
# Задание

Необходимо реализовать REST API, который позволяет отслеживать почтовые отправления.
В системе должны регистрировать почтовые отправления — письма, посылки — их передвижение между почтовыми отделениями, а также должна быть реализована возможность получения информации и всей истории передвижения конкретного почтового отправления.

#### Операции, которые должны быть реализованы:
- регистрации почтового отправления,
- его прибытие в промежуточное почтовое отделение,
- его убытие из почтового отделения
- его получение адресатом,
- просмотр статуса и полной истории движения почтового отправления.

#### Почтовое отправление определяется следующими свойствами:
- идентификатор,
- тип (письмо, посылка, бандероль, открытка),
- индекс получателя,
- адрес получателя,
- имя получателя.

#### Почтовое отделение характеризуется следующими свойствами:
- индекс,
- название,
- адрес получателя.

Сервис может быть реализован в виде JSON либо XML-сервиса на выбор.
Сервис может быть реализован при помощи стека Java EE или **Spring**.
СУБД для хранения данных может использоваться любая (PSQL).
Работа с данными должна быть выполнена с помощью ORM, библиотека может использоваться любая.
Приложение должно быть собрано при помощи Maven или Gradle.
Результатом работы должен быть war или ear-архив, который может быть размещен на сервер приложений. Для отладки и демонстрации может использоваться любой сервер приложений.
К приложению должно прилагаться описание его API — структура запросов и ответов, список допустимых операций, можно это реализовать в виде проекта SoapUI.
Код должен быть покрыт тестами минимум на 70% (приложить скрин покрытия)
________________________
# Комментари к заданию
Я посчитал немного глупым делать адрес получателя в почтовом отделении, ведь ПОСЫЛКА несет в себе информацию о адресе, а не отделение. Как в жизни, поэтому решено было сделать 1-N отделение к посылкам. 
Так же решено было сделать дополнительную сущность для аудита (истории) перемещения посылок.
#### Сущности:
- `Mail` - посылка
- `PostOffice` - почтовое отделение
- `MailHistory`- сущность аудита (истории изменений) 
#### Отношения:
```
Mail N -- 1 PostOffice
    1        1
    |       /
    N      N
MailHistory
```

________________________

# API Reference

Для проверки API и документации можно было бы использовать Swagger/OpenAPIv3, но не факт, что кто-то запустит этот jarник, и будет переходить по ссылкам, проверять API, поэтому просто напишу здесь
#### Дисклеймер: все конекчные точки идут по URI/api/v1/*
#### Получить все отделения

```http
  GET /postoffice/all
```
Ответ:

| Parameter      | Type     | Description                     | Уникальность |
|:---------------|:---------|:--------------------------------|:-------------|
| `id`           | `long`   | id почтового отделения          | ✓            |
| `created`      | `date`   | время создания                  |              |
| `updated`      | `date`   | время изменения                 |              |
| `index`        | `string` | индекс почтового отделения      | ✓            |
| `postName`     | `string` | название                        |              |
| `mailList`     | `array`  | список писем в данном отделении |              |

Пример ответа:
```JSON
[
    {
        "id": 1,
        "created": "2023-08-16T16:05:32.252386",
        "updated": "2023-08-16T16:05:32.252386",
        "index": "150006",
        "postName": "Почта Кукушка",
        "mailList": []
    },
    {
        "id": 2,
        "created": "2023-08-16T16:05:32.254295",
        "updated": "2023-08-16T16:05:32.254295",
        "index": "150007",
        "postName": "Почта Колотушка",
        "mailList": [
            {
                "id": 1,
                "created": "2023-08-16T16:05:41.197552",
                "updated": "2023-08-16T16:34:14.506861",
                "mailType": "LETTER",
                "indexRecipient": "150000",
                "addressRecipient": "мОцДом",
                "nameRecipient": "вм4теч",
                "stateType": "IN_PROCESSING",
                "postOffice": {
                    "id": 2,
                    "created": "2023-08-16T16:05:32.254295",
                    "updated": "2023-08-16T16:05:32.254295",
                    "index": "150007",
                    "postName": "Почта Колотушка"
                }
            }
        ]
    },
   ...
]
```


#### Создание отделения почты
Запрос
```http
  POST /postoffice/create
```

| Parameter      | Type     | Description                     | Уникальность |
|:---------------|:---------|:--------------------------------|:-------------|
| `index`        | `string` | индекс почтового отделения      | ✓            |
| `postName`     | `string` | название                        |              |

Пример запроса:
```json
{
    "index": "150001",
    "postName": "150000Название"
}
```
Пример ответа (думаю расшифровки не нужно, просто возвращает объект отделения почты (все поля))
```json
{
    "id": 1,
    "created": "2023-08-16T18:23:18.457417",
    "updated": "2023-08-16T18:23:18.457538",
    "index": "150001",
    "postName": "150000Название",
    "mailList": null
}
```

#### Регистрация посылки
Запрос
```http
  POST /postoffice/register_mail
```
| Parameter           | Type     | Description                                          | Проверка                                                       |
|:--------------------|:---------|:-----------------------------------------------------|:---------------------------------------------------------------|
| `mailType`          | `enum`   | `LETTER`/`PACKAGE`/`PARCEL`/`POSTCARD` - тип посылки |                                                                |
| `indexRecipient`    | `string` | индекс конечной точки (индекс получателя)            | можно отправить на несуществующий индекс, но потом его создать |
|  `addressRecipient` | `string` | адрес получателя                                     | проверяется в конце при получении посылки                      |
| `nameRecipient`    | `string` | имя получателя                                       | проверяется в конце при получении посылки                      |
| `indexOffice`    | `string` | индекс отедления, откуда отправляем посылку          | ошибка, если не существует отделения                           |

Пример запроса:
```json
{
    "mailType": "LETTER",
    "indexRecipient": "150000",
    "addressRecipient": "ПроектДом2",
    "nameRecipient": "vm4tech",
    "indexOffice": "150000"
}
```

| Parameter           | Type     | Description                                                             | Проверка                                                       |
|:--------------------|:---------|:------------------------------------------------------------------------|:---------------------------------------------------------------|
| `stateType`    | `enum` | `CREATED`/`IN_PROCESSING`/`AWAITNG_RECEIPT`/`RECEIVED` - статус посылки |                       |

Пример ответа. Все тоже самое, но есть еще статус
```json
{
  "id": 52,
  "created": "2023-08-16T17:07:23.708406",
  "updated": "2023-08-16T17:07:23.708455",
  "mailType": "LETTER",
  "indexRecipient": "150000",
  "addressRecipient": "ПроектДом2",
  "nameRecipient": "vm4tech",
  "stateType": "CREATED",
  "postOffice": {
    "id": 1,
    "created": "2023-08-16T18:23:18.457417",
    "updated": "2023-08-16T18:23:18.457538",
    "index": "32228",
    "postName": "Привет!"
  }
}
```
#### Отправка письма
Запрос
```http
  POST /postoffice/send_mail
```
| Parameter   | Type     | Description                              | Проверка                                                                                            |
|:------------|:---------|:-----------------------------------------|:----------------------------------------------------------------------------------------------------|
| `mailId`    | `long`   | `id` посылки                             | если нет, то ошибка                                                                                 |
| `fromIndex` | `string` | индекс отделения, кто отправляет посылку | используется для проверки, есть ли посылка в данном отделении                                       |
| `toIndex`   | `string` | индекс отделения, куда отправить посылку | Если `toIndex` == `mail.indexRecipient` => статус ставится `AWAITNG_RECEIPT`, иначе `IN_PROCESSING` |

Пример запроса
```json
{
    "mailId": 1,
    "fromIndex": "150000",
    "toIndex": "150001"
}
```
Пример ответа (просто возвращается посылка + измененный статус и отделение у посылки)
```json
{
  "id": 52,
  "created": "2023-08-16T17:07:23.708406",
  "updated": "2023-08-16T17:09:06.607409",
  "mailType": "LETTER",
  "indexRecipient": "150006",
  "addressRecipient": "мОцДом2",
  "nameRecipient": "вм4теч",
  "stateType": "AWAITNG_RECEIPT",
  "postOffice": {
    "id": 1,
    "created": "2023-08-16T16:05:32.252386",
    "updated": "2023-08-16T16:05:32.252386",
    "index": "150006",
    "postName": "Почта Кукушка"
  }
}
```

#### История посылки
Запрос
```http
  GET mail/history/{id}
```
| Parameter   | Type     | Description                                                         | Проверка |
|:------------|:---------|:--------------------------------------------------------------------|:---------|
| `id`        | `number` | `id` посылки, для которой надо посмотреть всю историю перемещений   |          |

Ответ

| Parameter      | Type     | Description                                                | Проверка |
|:---------------|:---------|:-----------------------------------------------------------|:---------|
| `id`           | `number` | `id` записи в истории                                      |          |
| `created`      | `date`   | время создания                                             |          |
| `updated`      | `date`   | время изменения                                            |          |
| `mail`         | `array`  | вся инфа о посылке                                         |          |
| `postOffice`   | `array`  | вся инфа о отделении                                       |          |
| `stateType`    | `string` | последний статус посылки, при отправке в данное отделение  |          |

пример ответа
```json
[
  {
    "id": 52,
    "created": "2023-08-16T17:07:23.71159",
    "updated": "2023-08-16T17:07:23.711652",
    "mail": {
      "id": 52,
      "created": "2023-08-16T17:07:23.708406",
      "updated": "2023-08-16T17:09:06.607409",
      "mailType": "LETTER",
      "indexRecipient": "150006",
      "addressRecipient": "мОцДом2",
      "nameRecipient": "вм4теч",
      "stateType": "AWAITNG_RECEIPT",
      "postOffice": {
        "id": 1,
        "created": "2023-08-16T16:05:32.252386",
        "updated": "2023-08-16T16:05:32.252386",
        "index": "150006",
        "postName": "Почта Кукушка"
      }
    },
    "postOffice": {
      "id": 3,
      "created": "2023-08-16T16:06:00.423736",
      "updated": "2023-08-16T16:06:00.423791",
      "index": "32228",
      "postName": "Привет!",
      "mailList": []
    },
    "stateType": "CREATED"
  },
  {
    "id": 53,
    "created": "2023-08-16T17:09:06.602714",
    "updated": "2023-08-16T17:09:06.602781",
    "mail": {
      "id": 52,
      "created": "2023-08-16T17:07:23.708406",
      "updated": "2023-08-16T17:09:06.607409",
      "mailType": "LETTER",
      "indexRecipient": "150006",
      "addressRecipient": "мОцДом2",
      "nameRecipient": "вм4теч",
      "stateType": "AWAITNG_RECEIPT",
      "postOffice": {
        "id": 1,
        "created": "2023-08-16T16:05:32.252386",
        "updated": "2023-08-16T16:05:32.252386",
        "index": "150006",
        "postName": "Почта Кукушка"
      }
    },
    "postOffice": {
      "id": 1,
      "created": "2023-08-16T16:05:32.252386",
      "updated": "2023-08-16T16:05:32.252386",
      "index": "150006",
      "postName": "Почта Кукушка",
      "mailList": [
        {
          "id": 2,
          "created": "2023-08-16T16:46:10.549563",
          "updated": "2023-08-16T16:47:24.721695",
          "mailType": "LETTER",
          "indexRecipient": "150006",
          "addressRecipient": "мОцДом2",
          "nameRecipient": "вм4теч",
          "stateType": "IN_PROCESSING",
          "postOffice": {
            "id": 1,
            "created": "2023-08-16T16:05:32.252386",
            "updated": "2023-08-16T16:05:32.252386",
            "index": "150006",
            "postName": "Почта Кукушка"
          }
        },
        {
          "id": 52,
          "created": "2023-08-16T17:07:23.708406",
          "updated": "2023-08-16T17:09:06.607409",
          "mailType": "LETTER",
          "indexRecipient": "150006",
          "addressRecipient": "мОцДом2",
          "nameRecipient": "вм4теч",
          "stateType": "AWAITNG_RECEIPT",
          "postOffice": {
            "id": 1,
            "created": "2023-08-16T16:05:32.252386",
            "updated": "2023-08-16T16:05:32.252386",
            "index": "150006",
            "postName": "Почта Кукушка"
          }
        }
      ]
    },
    "stateType": "AWAITNG_RECEIPT"
  }
]
```

#### Получение посылОК
Запрос
```http
  POST postoffice/get_mail_by_recipient
```
| Parameter           | Type     | Description                                       | Проверка                                                                   |
|:--------------------|:---------|:--------------------------------------------------|:---------------------------------------------------------------------------|
| `officeIndex`       | `string` | индекс отделения, куда мы пришли получать посылку | если нет такого отделения, то ошибка                                       |
| `address`           | `string` | адрес получателя                                  | используется для аутентификации пользователя, которому отправлена посылка  |
| `name`              | `string` | имя получаетеля                                   | используется для аутентификации пользователя, которому отправлена посылка  |

Пример запроса:
```json
{
    "officeIndex": "150000",
    "address": "ПроектДом2",
    "name": "vm4tech"
}
```
Пример ответа - список посылок, после чего они удаляются из текущего отеделения, но хранятся в истории.
```json
[
  {
    "id": 1,
    "created": "2023-08-16T18:44:44.253458",
    "updated": "2023-08-16T18:45:35.954955",
    "mailType": "LETTER",
    "indexRecipient": "150000",
    "addressRecipient": "ПроектДом2",
    "nameRecipient": "vm4tech",
    "stateType": "Received",
    "postOffice": {
      "id": 1,
      "created": "2023-08-16T18:23:18.457417",
      "updated": "2023-08-16T18:23:18.457538",
      "index": "150000",
      "postName": "Привет!"
    }
  }
]
```

__________
Если вы дочитали до конца, то поздравляю, возможно, вы потратили время не зря :)
В любом случае, жду вас в личном обсуждении в телеграмме:
- :mailbox_with_no_mail: [TG](https://t.me/vm4tech "vm4tech")
