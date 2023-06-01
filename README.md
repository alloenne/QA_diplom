# ПО и порядок запуска автотестов

## Установленное ПО:
IntelliJ IDEA
Docker Desktop
Google Chrome
Node.js 18.16.0

## Порядок запуска автотестов
1. Склонировать репозиторий с проектом.
2. Открыть проект через IntelliJ IDEA.
3. Запустить Docker Desktop.
4. Для запуска контейнеров с базами данных:
  * для запуска MySQL: в файле application.properties строчку "spring.datasource.url=jdbc:postgresql://localhost:5432/app" закомментировать, в файле build.gradle "systemProperty "db.url", System.getProperty("db.url", "jdbc:postgresql://localhost:5432/app")" закомментировать. Запустить в терминале команду docker-compose up -d mysqldb
  * для запуска PostgreSQL: в файле application.properties строчку "spring.datasource.url=jdbc:mysql://localhost:3306/app" закомментировать, в файле build.gradle "systemProperty "db.url", System.getProperty("db.url", "jdbc:mysql://localhost:3306/app")" закомментировать. Запустить в терминале команду docker-compose up -d postgresqldb
5. В новом терминале для запуска SUT выполнить команду:
  * Для БД MySQL: java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar ./aqa-shop.jar
  * Для БД PostgreSQL: java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar ./aqa-shop.jar
6. Открыть http://localhost:8080/.
7. Для запуска симулятора банковского сервиса запустить в новом терминале cd ./gate-simulator и npm start. Запуск може занять время.
8. В отдельном терминале запустить тесты:
  * Для БД MySQL: ./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"
  * Для БД PostgreSQL: ./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:5432/app"
9. Для сформирования и получения отчетов Allure выполнить команду: ./gradlew allureServe
10. После завершения работы остановить работу программ. 
11. Выполнить команду docker-compose down для остановки контейнеров.
