
**Сервис job4j_url_shortcut работает через REST API.** 

_Для реализации сервиса использован Spring Boot 2._

Реализованный функционал.

_1. Регистрация сайта._ 

Сервисом могут пользоваться разные сайты. Каждому сайту выдается пару пароль и логин.
Чтобы зарегистрировать сайт в систему отправляется запрос.
URL
POST /registration
C телом JSON объекта.
{site : "job4j.ru"}
Ответ от сервера.
{registration : true/false, login: УНИКАЛЬНЫЙ_КОД, password : УНИКАЛЬНЫЙ_КОД}
Флаг registration указывают, что регистрация выполнена или нет, то есть сайт уже есть в системе.

_1.1. Авторизация._

Во всех остальных вызовах сервис должен проверять авторизацию пользователя.
Авторизация выполнена через JWT- токен. 
Пользователь отправляет POST запрос с login и password и получает ключ.
Этот ключ отправляет в запросе в блоке HEAD.
Authorization: Bearer e25d31c5-db66-4cf2-85d4-8faa8c544ad6
 
_2.1 Регистрация URL._

Поле того, как пользователь зарегистрировал свой сайт он может отправлять на сайт ссылки и получать преобразованные ссылки.
Пример. 
Отправляем URL.
https://job4j.ru/TrackStudio/task/8993?thisframe=true
Получаем.
ZRUfdD2
Ключ ZRUfD2 ассоциирован с URL.

_2.2 Переадресация. Выполняется без авторизации._ 

Когда сайт отправляет ссылку с кодом в ответ нужно вернуть ассоциированный адрес и статус 302.

_3. Статистика._

В сервисе считается количество вызовов каждого адреса.
По сайту можно получить статистку всех адресов и количество вызовов этого адреса.


