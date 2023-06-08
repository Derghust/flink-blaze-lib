# Flink Blaze library

The Flink Blaze Library for Apache Flink is a comprehensive library designed to enhance
the development experience of Apache Flink applications written in Scala. 
This repository provides a set of utility methods and functions specifically crafted 
to assist developers in building clean and highly readable Flink applications.

## Work in Progress

Please note that the current repository is a work in progress. 
The development of the Flink Blaze Library for Apache Flink is still ongoing, 
and the features and functionalities provided may be incomplete or subject to change.

While the library aims to provide a comprehensive set of utility methods and functions 
for Apache Flink applications written in Scala, it is important to be aware that certain
aspects may be under active development or refinement.

We appreciate your understanding and patience as we continue to improve and expand 
the Scala Utils for Apache Flink library. Your feedback, suggestions, and contributions 
are highly valuable in shaping the future of this project.

Please consider regularly checking the repository for updates, 
as we aim to release stable versions that offer enhanced functionality, 
improved performance, and comprehensive documentation. Stay tuned for the official release,
where we will provide more stable and mature versions of the library.

In the meantime, feel free to explore the existing codebase,
experiment with the available utilities, and provide feedback to help us make the library
more robust and user-friendly.

Thank you for your interest in the Flink Blaze Library for Apache Flink application. 
We look forward to your involvement in this work in progress.

## Key Features

- Developer-Focused:
The library aims to simplify and streamline the development process for 
Apache Flink applications in Scala. It provides a collection of utility methods 
and functions carefully designed to help developers write efficient, maintainable, 
and readable code.
- Enhanced Readability: 
The utilities included in this library focus on improving the overall readability 
of Flink applications. By offering intuitive and expressive methods, developers 
can easily understand and collaborate on complex Flink codebases.
- Clean Code Practices: 
The repository promotes clean code practices, encouraging developers to adhere 
to established best practices while developing Flink applications. 
The utility methods provided enable developers to write concise, modular, and easily testable code.
- Broad Range of Utilities: 
The library covers a wide range of functionalities, including data transformations, 
serialization, event handling, time window operations, state management, and more. 
With this comprehensive set of utilities, developers can tackle various 
Flink application development challenges efficiently.

## How to Use

The Flink Blaze Library for Apache Flink library is currently under development 
and the official release has not yet been made. However, 
you can still leverage the library by cloning the repository and building it locally.

1. Clone the repository to your local machine:
```shell
$ git clone https://github.com/Derghust/flink-blaze-lib.git
```
2. Navigate to the cloned repository:
```shell
$ cd flink-blaze-lib
```
3. Build the library using gradle:
```shell
$ ./gradlew publishToMavenLocal
```
4. Include library to project
To include the locally built and released version of the library in your project, 
you can leverage the `gradle publishToMavenLocal` command to publish the library 
artifacts to your local Maven repository. Follow these steps:

- In your project's build configuration (e.g., `build.gradle`/`build.gradle.kts`), 
add the local Maven repository as a dependency source:
```kotlin
repositories {
    mavenLocal()
    // ... other repositories ...
}
```
- Add the library dependency to your project by specifying its coordinates:
```kotlin
dependencies {
    implementation("com.github.derghust:flinkblaze-operator:0.1.0")
    implementation("com.github.derghust:flinkblaze-impl:0.1.0")
    // ... other dependencies ...
}
```

## License

The Flink Blaze Library for Apache Flink is licensed under the MIT License, 
allowing you to freely use, modify, and distribute the library. 
Please refer to the repository's LICENSE file for more details.
