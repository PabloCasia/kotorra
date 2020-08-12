# Kotorra

Kotorra is a logger library for Kotlin Multiplatform with support for Android, iOS, Javascript and JVM targets.

## About the name

* [Quaker Parrot](https://en.wikipedia.org/wiki/Monk_parakeet) is a bird that can talk.
* [Quaker Parrot](https://en.wikipedia.org/wiki/Monk_parakeet) is called 
[Cotorra](https://es.wikipedia.org/wiki/Myiopsitta_monachus) in spanish.
* Loggers and Cotorras speak in words.
* So, I thought that a Logger library in Kotlin should be called Kotorra.

## Installation

// TODO

## Components to Know

Kotorra contains a `Tree`. A tree is a list of branches.

By default, Kotorra Tree contains a default branch for every platform, but you can add your own branches or even 
remove the default PlatformBranch.

### Log levels

| Level      | Method      |
|:--------------|:------------|
| VERBOSE       | Kotorra.v()  |
| DEBUG         | Kotorra.d()  |
| INFO          | Kotorra.i()  |
| WARNING       | Kotorra.w()  |
| ERROR         | Kotorra.e()  |

### PlatformBranch

`PlatformBranch` is the default implementation for every target:

#### Android
Works with `android.util.Log`, also known as [`Logcat`](https://developer.android.com/reference/android/util/Log)

#### iOS
Works with [`print`](https://developer.apple.com/documentation/swift/1541053-print)

#### Javascript
Works with `console.log`

#### JVM
Works with `java.util.logging.Logger`

## Getting Started

### How to use

#### Android
You must initialize Kotorra before using it. You can call initialize method in your Application instance:

```kotlin
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            // initialize Kotorra with default PlatformBranch
            Kotorra.initialize()
        } else {
            // initialize Kotorra with custom Branch
            Kotorra.initialize(listOf(MyCustomBranch()))
        }
    }
}
```

And start using it calling the log methods, some basic samples:

Android Platform Branch prints Class Name and Method Name so you can use it without arguments to debug with breadcrumbs:
```kotlin
Kotorra.v()
```
Supports TAGs to refine your log searches:
```kotlin
Kotorra.v("Test", "SomeTag")
```  
Supports thread name:
```kotlin
Kotorra.isThreadNameVisible = true
Kotorra.v("Test")
```  
Supports throwable stack traces:
```kotlin
Kotorra.e("Throwable test", throwable = NotImplementedError())
``` 

Alternatively you can also construct them with Kotorra DSL:
```kotlin
Kotorra.e {
    message = "Throwable test"
    tag = "SomeTag"
    throwable = NotImplementedError()
}   
``` 
  
Some samples and the output:

```kotlin
object TestClass {
    fun function() {
        Kotorra.v()

        Kotorra.v("Test 1")

        Kotorra.v("Test 2", "SomeTag")

        Kotorra.isThreadNameVisible = true
        Kotorra.v("Test 3")
        Kotorra.v("Test 4")
        Kotorra.isThreadNameVisible = false

        Kotorra.v("Test 5")

        Kotorra.v {
            message = "Test 6"
            isThreadNameVisible = true
        }

        Kotorra.e("Error test")

        Kotorra.e("Throwable test", throwable = NotImplementedError())
        Kotorra.e {
            message = "Throwable test"
            throwable = NotImplementedError()
        }
    }
}
``` 

![image](/assets/android.png)
 
#### iOS
// TODO

#### Javascript
// TODO

#### JVM
// TODO

## Advanced Topics

### Custom branches

You can add custom `Branch` to Kotorra, you only need to extend `Branch`:

#### Android

````kotlin
class MyCustomBranch : Branch() {
    override fun performLog(priority: Kotorra.Level, tag: String?, throwable: Throwable?, message: String?) {
        // Do something
    }
}
````

#### iOS
// TODO

#### Javascript
// TODO

#### JVM
// TODO

## License

```
Copyright (C) 2020 Pablo García Fernández

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
