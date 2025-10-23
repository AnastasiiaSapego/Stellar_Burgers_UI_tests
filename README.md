# Stellar Burgers UI Autotests (Java + Selenium)

Автотесты UI для веб-приложения **Stellar Burgers**  
Тестируются сценарии регистрации, входа и переключения разделов конструктора.

---

## Технологии

| Компонент | Версия|
| --- | --- |
| Java | 11 |
| Maven | 3.9.x |
| Selenium | 4.25.0 |
| JUnit | 4.13.2 |
| WebDriverManager | 5.8.0 |
| Allure | 2.27.0 (плагин: 2.12.0) |
| REST-assured | 5.4.0 |
| Commons Lang | 3.14.0 |

---

## Запуск тестов

**Chrome:**
"mvn clean test"

**Yandex:**
"mvn -Pyandex \
-DYA_DRIVER_PATH="/Users/<you>/Tools/yandexdriver" \
-DYA_BROWSER_BIN="/Applications/Yandex.app/Contents/MacOS/Yandex" \
test"


