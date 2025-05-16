![Java](https://img.shields.io/badge/Java-17-blue )
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green )
![Kafka](https://img.shields.io/badge/Kafka-lightgrey )
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-blueviolet )
![Gradle](https://img.shields.io/badge/Gradle-orange )

# Microservices Platform

**Microservices Platform** — это распределённая система управления владельцев и их питомц, реализованная с использованием микросервисной архитектуры. Проект демонстрирует применение современных подходов к построению масштабируемых приложений на Java + Spring Boot.

---

## 🧱 Архитектура

Проект состоит из трёх независимых микросервисов:

| Микросервис       | Описание |
|------------------|----------|
| `Cat Service`     | Сервис для работы с данными с питомцами |
| `Owner Service`   | Сервис для управления информацией о владельцах |
| `Gateway Service` | Центральный сервис: REST API, авторизация, внешние endpoint'ы |

Микросервисы обмениваются данными через **Apache Kafka**, что обеспечивает асинхронную коммуникацию и повышает отказоустойчивость системы.

---

## 🚀 Технологии

- **Java 17**
- **Spring Boot** (Web, Data JPA, Security)
- **PostgreSQL**
- **Apache Kafka**
- **Gradle**
- **JPA / Hibernate**
- **REST API**
- **Spring Security**
- **Javadoc**

---

## 🔨 Возможности

- Создание, обновление, удаление и просмотр информации о котиках и владельцах
- Разграничение прав доступа по ролям (через Spring Security)
- Асинхронная коммуникация между микросервисами через Kafka
- Документация API с помощью Javadoc
- Поддержка независимого запуска каждого микросервиса

---

✅ Чему научился
- Разрабатывать микросервисные архитектуры
- Использовать Kafka для межсервисного взаимодействия
- Работать с JPA, Hibernate и PostgreSQL
- Обеспечивать безопасность с помощью Spring Security
- Собирать проекты через Gradle
- Писать документацию и тестировать REST API
