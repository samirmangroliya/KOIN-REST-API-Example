**Kotlin + MVVM Clean Architecture + Flow + Koin**

Android App Development using Clean MVVM Architecture for REST API Call Example

**Coding Language: Kotlin**

**Library Used:**

Retrofit => For Rest API call
Koin => Dependency Injection 
Koin Test => Instrumention Testing using Koin Test Libarary

**Code Structure -> Clean MVVM Architecture**

app    -> MyApp class

data   -> ApiService
          Model
          Repository Implementation 

domain -> Repository Defination
          UseCase

di     ->  AppModule
           Network Moduel
           
presentation -> Activity
                Adapter
                utils
                viewmodels

**Testing Environment**

ApiIntegrationKoinTest using Koin Test Library
ApiIntegrationTest using runblocking

**Unit Testing**

Filter function JUnit Testing





                
