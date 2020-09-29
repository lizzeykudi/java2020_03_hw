# Message System
## Структура модуля
* `hw17_message_server` - корневой модуль
	* `MessageSystemCommon` - модуль с общими классами данных для Message System
	* `MessageClient` - модуль с общими классами для клиентов Message System. Используется в реализациях клиентов - `Frontend` и `DBServer`
	* `Frontend` - запускаемый модуль с веб-интерфейсом, клиент Message System
	* `DBServer` - запускаемый модуль, реализующий работу с БД, клиент Message System
	* `MessageServer` - запускаемый модуль, сервер сообщений в Message System

## Порядок запуска в Intellij IDEA
1. Импортировать корневой `pom.xml` как Maven-проект. В результате должны проиндексироваться все модули, перечисленные выше.
2. Для запуска сервера в модуле `MessageServer` запустить `ru.otus.MessageServerRunner`. Его следует запустить раньше, чем клиентов.
3. Для запуска клиента DBServer в модуле `DBServer` запустить `ru.otus.dbserver.DbServerRunner`. 
4. Для запуска клиента Frontend в модуле `Frontend` запустить `ru.otus.frontend.FrontendRunner`. 
5. Когда все запущены, открыть в браузере адрес `localhost:8080`.