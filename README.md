[![kotlin](https://img.shields.io/github/languages/top/bikcodeh/ToDoApp.svg?style=for-the-badge&color=blueviolet)](https://kotlinlang.org/) [![Android API](https://img.shields.io/badge/api-26%2B-brightgreen.svg?style=for-the-badge)](https://android-arsenal.com/api?level=26)
# Mubi
#### Main Branch: [![Build status](https://build.appcenter.ms/v0.1/apps/544ffef5-a298-4f4a-8c94-9e41c3f845d0/branches/main/badge)](https://appcenter.ms)

## :star: Features

- [x] Display tv shows by different 4 filters using pagination
- [x] Display detail of a specific tv show
- [x] Display the seasons of a tv show
- [x] Profile
- [x] Login
- [x] Search
- [x] Local persistance
- [x] Dark theme

:runner: For run the app just clone the repository and execute the app on Android Studio.

### :bookmark_tabs: Requirements to install the app
- Use phones with Android Api 26+
- Having an internet connection

##### :open_file_folder: This application was developed using Kotlin and uses the following components:
- Jetpack compose
- Coroutines
- Clean architecture (Domain, Data, Presentation)
- MVVM
- App center - Crashlytics - Continuos integration
- Repository pattern
- Use cases
- StateFlow
- Mutable State
- Jetpack navigation compose
- Lottie animations
- Timber (Logs)
- Dagger Hilt (Dependency injection)
- Coil (Load images)
- Retrofit (HTTP requests)
- Pagination
- Unit testing (Mockk, Thruth, Coroutines tests)

## Structure per module
#### App
- [**com**](com)
    - [**bikcodeh**](com/bikcodeh)
        - [**melichallenge**](com/bikcodeh/melichallenge)
                
### Presentation
- [**com**](com)
    - [**bikcodeh**](com/bikcodeh)
        - [**mubi**](com/bikcodeh/mubi)
            - [**presentation**](com/bikcodeh/mubi/presentation)
                - [**components**](com/bikcodeh/mubi/presentation/components)
                - [**navigation**](com/bikcodeh/mubi/presentation/navigation)
                - [**screens**](com/bikcodeh/mubi/presentation/screens)
                    - [**detail**](com/bikcodeh/mubi/presentation/screens/detail)
                    - [**home**](com/bikcodeh/mubi/presentation/screens/home)
                        - [**components**](com/bikcodeh/mubi/presentation/screens/home/components)
                    - [**login**](com/bikcodeh/mubi/presentation/screens/login)
                    - [**profile**](com/bikcodeh/mubi/presentation/screens/profile)
                    - [**search**](com/bikcodeh/mubi/presentation/screens/search)
                    - [**season**](com/bikcodeh/mubi/presentation/screens/season)
                    - [**splash**](com/bikcodeh/mubi/presentation/screens/splash)
                - [**theme**](com/bikcodeh/mubi/presentation/theme)
                - [**util**](com/bikcodeh/mubi/presentation/util)
                    - [**base**](com/bikcodeh/mubi/presentation/util/base)
                    - [**extension**](com/bikcodeh/mubi/presentation/util/extension)

#### Data
- [**com**](com)
    - [**bikcodeh**](com/bikcodeh)
        - [**mubi**](com/bikcodeh/mubi)
            - [**data**](com/bikcodeh/mubi/data)
                - [**di**](com/bikcodeh/mubi/data/di)
                - [**local**](com/bikcodeh/mubi/data/local)
                    - [**db**](com/bikcodeh/mubi/data/local/db)
                        - [**converter**](com/bikcodeh/mubi/data/local/db/converter)
                        - [**dao**](com/bikcodeh/mubi/data/local/db/dao)
                    - [**preferences**](com/bikcodeh/mubi/data/local/preferences)
                - [**mappers**](com/bikcodeh/mubi/data/mappers)
                - [**pagination**](com/bikcodeh/mubi/data/pagination)
                - [**remote**](com/bikcodeh/mubi/data/remote)
                    - [**interceptor**](com/bikcodeh/mubi/data/remote/interceptor)
                    - [**response**](com/bikcodeh/mubi/data/remote/response)
                    - [**service**](com/bikcodeh/mubi/data/remote/service)
                - [**repository**](com/bikcodeh/mubi/data/repository)
                - [**util**](com/bikcodeh/mubi/data/util)

####  Domain 

- [**com**](com)
    - [**bikcodeh**](com/bikcodeh)
        - [**mubi**](com/bikcodeh/mubi)
            - [**domain**](com/bikcodeh/mubi/domain)
                - [**common**](com/bikcodeh/mubi/domain/common)
                - [**di**](com/bikcodeh/mubi/domain/di)
                - [**entity**](com/bikcodeh/mubi/domain/entity)
                - [**model**](com/bikcodeh/mubi/domain/model)
                - [**repository**](com/bikcodeh/mubi/domain/repository)
                - [**usecase**](com/bikcodeh/mubi/domain/usecase)

#### Core test
- [**com**](com)
    - [**bikcodeh**](com/bikcodeh)
        - [**mubi**](com/bikcodeh/mubi)
            - [**core_test**](com/bikcodeh/mubi/core_test)
                - [**util**](com/bikcodeh/mubi/core_test/util)

## :sun_with_face: Screenshots Light theme
 | Splash |     Home    |  Login  |   Search |  Detail |
 | :----: | :---------: | :-------: | :-----------: | :-----: |
 |<img src="https://raw.githubusercontent.com/Bikcodeh/Mubi/main/assets/screenshots/splash.png" align="left" height="300" width="1600">|<img src="https://raw.githubusercontent.com/Bikcodeh/Mubi/main/assets/screenshots/home.png" align="left" height="300" width="1600">|<img src="https://raw.githubusercontent.com/Bikcodeh/Mubi/main/assets/screenshots/login.png" align="left" height="300" width="1600">|<img src="https://raw.githubusercontent.com/Bikcodeh/Mubi/main/assets/screenshots/search.png" align="left" height="300" width="1600">|<img src="https://raw.githubusercontent.com/Bikcodeh/Mubi/main/assets/screenshots/detail.png" align="left" height="300" width="1600"> |

| Leave confirm |   Profile  |   Searched | Season  | Pagination error | 
| :-----------------:| :----------------------: | :----------------:| :----------------:| :----------------:|
<img src="https://raw.githubusercontent.com/Bikcodeh/Mubi/main/assets/screenshots/leave_confirm.png" align="left" height="300" width="170">| <img src="https://raw.githubusercontent.com/Bikcodeh/Mubi/main/assets/screenshots/profile.png" align="left" height="300" width="170"> |  <img src="https://raw.githubusercontent.com/Bikcodeh/Mubi/main/assets/screenshots/searched.png" align="left" height="300" width="170"> | <img src="https://raw.githubusercontent.com/Bikcodeh/Mubi/main/assets/screenshots/season.png" align="left" height="300" width="170"> | <img src="https://raw.githubusercontent.com/Bikcodeh/Mubi/main/assets/screenshots/error_pagination.png" align="left" height="300" width="170"> |

| Error |
| :------------:| 
|<img src="https://raw.githubusercontent.com/Bikcodeh/Mubi/main/assets/screenshots/error_internet.png" align="left" height="300" width="170">|

## :new_moon_with_face: Screenshots Dark Mode

 | Splash |     Home    |  Login  |   Search |  Detail |
 | :----: | :---------: | :-------: | :-----------: | :-----: |
 |<img src="https://raw.githubusercontent.com/Bikcodeh/Mubi/main/assets/screenshots/splash.png" align="left" height="300" width="1600">|<img src="https://raw.githubusercontent.com/Bikcodeh/Mubi/main/assets/screenshots/home_dark.png" align="left" height="300" width="1600">|<img src="https://raw.githubusercontent.com/Bikcodeh/Mubi/main/assets/screenshots/login_dark.png" align="left" height="300" width="1600">|<img src="https://raw.githubusercontent.com/Bikcodeh/Mubi/main/assets/screenshots/search_dark.png" align="left" height="300" width="1600">|<img src="https://raw.githubusercontent.com/Bikcodeh/Mubi/main/assets/screenshots/detail_dark.png" align="left" height="300" width="1600"> |

| Leave confirm |   Profile  |   Searched | Season  | Pagination error | 
| :-----------------:| :----------------------: | :----------------:| :----------------:| :----------------:|
<img src="https://raw.githubusercontent.com/Bikcodeh/Mubi/main/assets/screenshots/leave_confirm_dark.png" align="left" height="300" width="170">| <img src="https://raw.githubusercontent.com/Bikcodeh/Mubi/main/assets/screenshots/profile_dark.png" align="left" height="300" width="170"> |  <img src="https://raw.githubusercontent.com/Bikcodeh/Mubi/main/assets/screenshots/searched_dark.png" align="left" height="300" width="170"> | <img src="https://raw.githubusercontent.com/Bikcodeh/Mubi/main/assets/screenshots/season_dark_mode.png" align="left" height="300" width="170"> | <img src="https://raw.githubusercontent.com/Bikcodeh/Mubi/main/assets/screenshots/error_pagination_dark.png" align="left" height="300" width="170"> |

| Error |
| :------------:| 
|<img src="https://raw.githubusercontent.com/Bikcodeh/Mubi/main/assets/screenshots/error_internet_dark.png" align="left" height="300" width="170">|


## :dart: Architecture

The application is built using Clean Architeture pattern based on [Architecture Components](https://developer.android.com/jetpack/guide#recommended-app-arch) on Android. The application is divided into three layers:

![Clean Arquitecture](https://devexperto.com/wp-content/uploads/2018/10/clean-architecture-own-layers.png)

- Domain: This layer contains the business logic of the application, here we define the data models and the use cases.
- Data: This layer contains the data layer of the application. It contains the database, network and the repository implementation.
- Presentation: This layer contains the presentation layer of the application.

## License

MIT

**Bikcodeh**
