# Sanad — Parent App
 
Android companion app for Sanad, an e-learning platform. Parents use the app to link their children's student accounts, follow their enrolled courses and progress, and stay updated through a live announcements/notifications feed.
 
Native Android, written in Kotlin with Jetpack Compose, built on Clean Architecture with Hilt for dependency injection.
 
## Features
 
- **Authentication** — email/password sign up and sign in, OTP email verification, and password reset.
- **Add a student (QR linking)** — scan the **Parent Key** QR code shown in the Student app (ML Kit Barcode Scanning + CameraX) to link a child's account to the parent's profile.
- **Multi-student support** — link and follow more than one child from a single parent account.
- **Student courses** — view each linked child's enrolled courses and individual course details/progress.
- **Feed** — a home feed of course-related updates and events for linked students.
- **Notifications** — in-app notifications feed.
- **Profile** — view and manage the parent account.
## Tech Stack
 
| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose, Material 3 |
| Dependency Injection | Dagger Hilt |
| Networking | Ktor Client (CIO/OkHttp engine), kotlinx.serialization, Gson |
| Local persistence | Jetpack DataStore (Preferences) |
| Image loading | Glide |
| QR / Barcode | ML Kit Barcode Scanning + CameraX |
| Navigation | Jetpack Navigation Compose |
| Testing | JUnit, Espresso, Compose UI Test |
 
## Architecture
 
The app follows **Clean Architecture** with a feature-first package structure. Each feature (`auth`, `feed`, `student`, `profile`, `notifications`, `core`) is split into three layers:
 
```
feature/
├── data/           # DTOs, remote data sources, repository implementations
├── domain/         # Models, repository interfaces, use cases
└── presentation/   # Compose screens, ViewModels, UI state/events
```
 
- **MVVM** on top of the domain layer — screens expose state via `ViewModel`s.
- **Hilt** provides use cases, repositories, and network clients across features via constructor injection.
- **Use cases** encapsulate individual actions (e.g. `SignIn`, adding/removing a linked student, fetching a student's courses).
## Getting Started
 
### Prerequisites
- Android Studio (Koala or newer recommended)
- JDK 8+
- Android SDK 34 (minSdk 26)
### Setup
```bash
git clone https://github.com/Abdelaziz237/for-parents.git
cd for-parents
```
Open the project in Android Studio, let Gradle sync, and run the `app` configuration on an emulator or device.
 
## Project Info
 
- **Package name:** `com.sanadedu.parent`
- **Companion app:** [Sanad — Student App](https://github.com/Abdelaziz237/for-students), which generates the Parent Key QR code this app scans to link accounts.
- **License:** Apache 2.0 — see [LICENSE](./LICENSE).
## Disclaimer
 
This repository showcases mobile engineering work built for the Sanad startup. Backend endpoints and production credentials are not included.
 
