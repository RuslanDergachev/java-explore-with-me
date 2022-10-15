# java-explore-with-me
Template repository for ExploreWithMe project.
https://github.com/RuslanDergachev/java-explore-with-me/pull/1

Приложение публикации событий и поиска участников в событиях.
Проект создан с использованием Spring Boot.

Проект состоит из двух модулей:
ewm-service - обрабатывает события для публикации, запросы на участие в событиях
пользователя. Сохраняет статистику просмотра событий в сервис статистики.
stat-service - сервис статистики, сохраняет историю просмотров событий, возвращает
статистику просмотров по параметрам.

Приложение запускается через docker compose командой docker compose up.
