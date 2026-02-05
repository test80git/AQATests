# Getting Started
## Properties
### allure.properties
Куда будут сохраняться тесты после выполнения. Изначально всегда генерирует в папку *"build/allure-results"* и 
генерирует отчет.  
```properties
allure.results.directory=build/allure-results
```
Вызывая AllureReport allureServe он смотрит в эту директорию.  
![img.png](Gradle-verification-allure.png)
### config.properties
Глобальные настройки для проекта. 
```properties
base.url=https://www.saucedemo.com/
base.test.video.path=build/testvideo/
browser=chromium //firefox
headless=false // запускать тесты без отображения
slow.motion=50 // скорость 
timeout=10000 // мс Время проверки
video=true // Записывать видео или нет
```
### junit-platform.properties

```properties
junit.jupiter.execution.parallel.enabled=true
junit.jupiter.execution.parallel.mode.default=same_thread
junit.jupiter.execution.parallel.mode.classes.default=concurrent
junit.jupiter.execution.parallel.config.strategy=dynamic
junit.jupiter.execution.parallel.config.dynamic.factor=0.5
```
