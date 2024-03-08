# Kotlin Multiplatform - Rock, Paper, Scissors Game

This Kotlin Multiplatform project implements a functional and reactive Rock, Paper, Scissors game designed for Android and iOS platforms. The code is open for use and further development.

## Directory Structure

- `/composeApp` contains code shared across all Compose Multiplatform applications:
  - `commonMain`: Code shared across all platforms.
  - `androidMain`: Kotlin code compiled exclusively for the Android platform.
  - `iosMain`: Kotlin code specific to the iOS platform (e.g., calls to Apple's CoreCrypto).

## iOS Applications

- `/iosApp` houses the iOS applications. Even when sharing the UI with Compose Multiplatform, this entry point is necessary for the iOS app. SwiftUI code and any iOS-specific implementations should be added here.

## Game Description

The project implements a Rock, Paper, Scissors game where players can choose between the three options. The game operates in a functional and reactive manner, utilizing Kotlin Flows. The UI is built using Kotlin Compose Multiplatform, and the game logic is provided at the `commonMain` level to ensure it is shared across all platforms.

## Further Development and Usage

This project is open for extensions and customizations. Developers are encouraged to continue developing the game, add features, or adapt it for their own projects. The flexibility of Kotlin Multiplatform allows others to use and modify the code for different platforms.

## License

This Rock, Paper, Scissors game is licensed under the Apache License 2.0. You can find the full text of the license in the `LICENSE` file accompanying this project. The Apache 2.0 license grants other developers the freedom to use, adapt, and further develop the code, subject to the terms and conditions specified in the license. We encourage the community to contribute to the improvement and expansion of this project.
