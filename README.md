# timber-logging-to-file

[![](https://jitpack.io/v/markowanga/timber-logging-to-file.svg)](https://jitpack.io/#markowanga/timber-logging-to-file)
[![Open Source Love](https://badges.frapsoft.com/os/v2/open-source.svg?v=103)](https://github.com/ellerbrock/open-source-badges/)

Simple library which save encrypted timber logs to file. Best way to verify what's happen when
tester have hard to reproduce error and Crashlytics doesn't contain enough information.
In difficult examples it is possible to use it in production. 

## Add dependency to gradle

```kotlin
implementation("com.github.markowanga:timber-logging-to-file:1.3.0")
```

## Basic usage of library

Library provide simple `Timber.Tree` called `LogToFileTimberTree`.

Below example shows how to plant `LogToFileTimberTree`:

```kotlin
fun initLogger() {
    Timber.plant(
        LogToFileTimberTree(ExternalLogStorageProvider(context).getStorageDirectory())
    )
}
```

## Encrypted logs

Sometimes logs contain any secure data and user cannot have access to read it. Library contain
simple `TextCrypt` class which can encrypt logs before append to file. There are two TextCrypters
implemented in library, of course the interface can be implemented with other custom crypt methods.

Logs are saved to file line by line, it means that each line should be decoded separately.

### Base64TextCrypt

The simple encryption -- easy to decode, but simple user can't read it.

Example of use:

```kotlin
fun initLogger() {
    Timber.plant(
        LogToFileTimberTree(
            ExternalLogStorageProvider(context).getStorageDirectory(),
            Base64TextCrypt()
        )
    )
}
```

### CipherTextCrypt

More advanced TextCrypt. It uses `javax.crypto.Cipher` with `AES/ECB/PKCS5Padding` transformation.
Logs are protected by password.

Example of use:

```kotlin
fun initLogger() {
    Timber.plant(
        LogToFileTimberTree(
            ExternalLogStorageProvider(context).getStorageDirectory(),
            CipherTextCrypt("test1234test1234")
        )
    )
}
```

## Log file names

Library support file name based on current date and any variant can be implemented.

There are two implemented `LogFileNameProvider` in library

### DailyLogFileNameProvider

The best way to divide logs for days

```kotlin
class DailyLogFileNameProvider(
    private val dateTimeFormatter: DateTimeFormatter = DEFAULT_FORMATTER,
    prefix: String = DEFAULT_PREFIX,
    extension: String = DEFAULT_EXTENSION
) : LogFileNameProvider(prefix, extension) { /**/ }
```

### ConstantDateTimeLogFileNameProvider

The best way to divide logs per constant date ??? ex. datetime when application starts

```kotlin
class ConstantDateTimeLogFileNameProvider(
    private val dateTime: LocalDateTime = LocalDateTime.now(),
    private val dateTimeFormatter: DateTimeFormatter = DEFAULT_FORMATTER,
    prefix: String = DEFAULT_PREFIX,
    extension: String = DEFAULT_EXTENSION
) : LogFileNameProvider(prefix, extension) { /**/ }

```

## Where to find files with logs
There are two type of paths provided by implementation of `StorageProvider`

### InternalLogStorageProvider
```kotlin
InternalLogStorageProvider(context).getStorageDirectory()
```
It's classic internal memory of android app, it isn't available to read in release mode

### ExternalLogStorageProvider
```kotlin
ExternalLogStorageProvider(context).getStorageDirectory()
```
External location of files ??? default Location is `Android/data/{applicationId}/files/app-logs`.
