# Code created in the course Building Web Applications in Kotlin Using Ktor by Kevin Jones

## About

This project contains the codes created during the Pluralsight course [Building Web Applications in Kotlin Using Ktor](https://www.pluralsight.com/courses/building-web-applications-kotlin-ktor) presented by kevin Jones. 

## Learning objective this course

Try creating REST API using Ktor framework for Kotlin.

## Tech-stack

> [Kotlin](https://kotlinlang.org/)  
> [Gradle](https://gradle.org/)  
> [Ktor](https://ktor.io/)  
> [Keycloak](https://www.keycloak.org/)  
> [Mustache](https://github.com/spullara/mustache.java)  
> [Koin](https://insert-koin.io/)  
> [Fuel](https://fuel.gitbook.io/documentation/)  
> [Gson](https://github.com/google/gson)  
> [Jackson](https://github.com/FasterXML/jackson)  
> [klaxon](https://github.com/cbeust/klaxon)  
> [Guava](https://github.com/google/guava)  
> [Arrow](https://arrow-kt.io/)  
> [JUnit5](https://junit.org/junit5/)  
> [Spek](https://www.spekframework.org/)  
> [Kluent](https://markusamshove.github.io/Kluent/)  
> [MockK](https://mockk.io/)  

## Running

1. Clone the git project through the terminal:

```shell
git clone https://github.com/vitormbgoncalves/ktor-pluralsight-project.git
cd ktor-pluralsight-project
```

2. Then build the application with the command below:;

```shell
./gradlew build
```

3. Now you are ready to lunch it:

```shell
./gradlew run
```

4. After executing the project, access the following address in the browser:

`http://localhost:9083/`

## Quick note

This project was developed and runs on Linux.

To run this project it is necessary to have an instance of Keycloak running locally or in Docker container and set configuration in: `todolist-web/src/main/kotlin/keycloakConf.kt` and `todolist-restapi/src/main/resources/application.conf` https://www.keycloak.org/docs/latest/server_installation/

Courses available at Pluralsight!

[Click here and access the course](https://www.pluralsight.com/courses/building-web-applications-kotlin-ktor)


