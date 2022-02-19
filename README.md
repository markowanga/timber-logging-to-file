# timber-logging-to-file
[![](https://jitpack.io/v/markowanga/timber-logging-to-file.svg)](https://jitpack.io/#markowanga/timber-logging-to-file)

Simple library which save timber logs to file.

## Add dependency to gradle
```kotlin
implementation("com.github.markowanga:timber-logging-to-file:1.0.3")
```

## Basic usage of library
Library provide simple `Timber.Tree` called `LogToFileTimberTree`.
Below example shows how to

```kotlin
    private fun initLogger() {
        Timber.plant(Timber.DebugTree())
        Timber.plant(
            LogToFileTimberTree(LogManager.getExternalLogsDirectory(this))
        )
        Timber.i("Hello log ;) !!!")
    }
```