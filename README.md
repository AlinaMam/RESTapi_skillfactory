# Банковское приложение
## Структура проекта
### controller

`UserBalanceController` - REST Controller

### exception
`GlobalExceptionHandling` - класс, предоставляющий функциональность Global Exception Handler-a<br>
`IncorrectData` - тип объекта, который добавляется в HTTP Response Body<br>
`NoSuchElementException` - exception, на который должен реагировать метод<br>
`NotEnoughMiney` - exception, на который должен реагировать метод

### model
`UserBalance` - pojo-класс, отображающий таблицу БД

### repository
`UserBalanceRepository` - слой DAO, для взаимодействия с БД

#### application.property
Файл для хранения параметров конфигурации приложения<br>
#### Структура БД

![Screenshot](https://github.com/AlinaMam/RESTapi_skillfactory/blob/master/src/main/resources/images/Структура%20БД.png)



