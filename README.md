# MyRest

Automation of restaurants search, table reservation and interaction with staff

![.github/workflows/main.yml](https://github.com/ItmoDreamTeam/MyRest/workflows/.github/workflows/main.yml/badge.svg)
![GitHub contributors](https://img.shields.io/github/contributors/ItmoDreamTeam/MyRest)
![GitHub last commit](https://img.shields.io/github/last-commit/ItmoDreamTeam/MyRest)

![GitHub issues](https://img.shields.io/github/issues/ItmoDreamTeam/MyRest)
![GitHub closed issues](https://img.shields.io/github/issues-closed/ItmoDreamTeam/MyRest)

![GitHub pull requests](https://img.shields.io/github/issues-pr/ItmoDreamTeam/MyRest)
![GitHub closed pull requests](https://img.shields.io/github/issues-pr-closed/ItmoDreamTeam/MyRest)

## Production

The website is available at http://206.81.24.133

The server REST API is available at http://206.81.24.133:9339

## Build

_Requirements_: Java 11

In order for clients to communicate with the server deployed locally, the server's host and port must be specified
in `org.itmodreamteam.myrest.shared.ClientProperties.Server` (module `shared`)

### Server

`./gradlew :server:build`

### Website

`./gradlew :js:build`

### Android App

_Requirements_: Android SDK (SDK location should be specified in local.properties file)

`./gradlew :android:build`

## Deploy

_Requirements_: latest Docker and Docker Compose

### Server

The REST API port is 9339 as configured in docker-compose.yml

```shell
export PROFILE=local
export DB_PASSWORD=123
cd server
docker-compose -p myrest up -d --build
cd ..
```

### Website

```shell
export PROFILE=local
export PORT=8080
cd js
docker-compose -p myrest-web up -d --build
cd ..
```

### Android App

After the android module has been built, the location of the artifact to deploy on an Android smartphone
is `android/build/outputs/apk/debug/android-debug.apk`.
