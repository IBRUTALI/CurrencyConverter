# :dollar: CurrencyConverter

CurrencyConverter - это приложение для конвертации валюты.  

# Обзор приложения:

## 1 . 💻 Экран выбора валюты

  :large_blue_circle: Пользователь может вводить значение, которое хочет конвертировать  
  :large_blue_circle: Пользователь может выбрать валюты, которые хочет конвертировать    
  :large_blue_circle: Кнопка конвертации станет активной только тогда, когда пользователь введет какое-либо числовое значение и выберет разные вылюты    
  :large_blue_circle: Кнопка конвертации, если активна, перекидывает пользователя на экран "Итог конвертации", где и будет проходить конвертация    
  :large_blue_circle: Пока данные загружаются пользователь видит прогресс бар   
  :large_blue_circle: Если данные не удалось загрузить, пользователь видит причину ошибки в виде тоста и также имеет возможность повторить запрос   
  :large_blue_circle: Если ошибка загрузки данных связана с интернетом, данные о валютах загружаются из локальной базы данных     
  
<p align="center">
  <img align="center" width="31.6%" src="https://github.com/user-attachments/assets/964b8cab-f5b7-4433-8a3b-c3d2ebb725c2">
  <img align="center" width="30%" src="https://github.com/user-attachments/assets/6c9579a7-81b5-434c-96dc-4df95a87e03e">
</p>
  
## 2 . 🔍 Экран "Итог конвертации"  

:large_blue_circle: Пользователь видит результат конвертации в случае успеха.  
:large_blue_circle: Пока данные загружаются пользователь видит прогресс бар   
:large_blue_circle: Если данные не удалось загрузить, пользователь видит причину ошибки в виде тоста и также имеет возможность повторить запрос    

<p align="center">
  <img align="center" width="30%" src="https://github.com/user-attachments/assets/025a765e-ad74-4a77-940f-21af2f9e2f31">
</p>

## 🛠️ Стек:
- Kotlin
- View
- Room
- Jetpack Navigation
- Dagger2
- MVVM
- Clean Architecture
- Flow, Channels
- Coroutines
- Retrofit, okHttp3
