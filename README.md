# 📰 News App – Android Application

A modern, production-ready Android News application built using **MVVM + Clean Architecture**. The application is designed to fetch the latest news from a remote API and cache it locally in a Room database to ensure seamless offline access.

---

## 🚀 Overview

This project serves as a robust example of scalable and maintainable Android development by combining industry-standard libraries and architectural patterns:

* **MVVM (Model-View-ViewModel):** For structured UI separation and lifecycle awareness.
* **Clean Architecture:** For a strict separation of concerns, making the codebase highly testable and adaptable.
* **Retrofit:** For reliable network communication and API integration.
* **Room Database:** For local caching, enabling a complete offline-first user experience.

---

## 🏗️ Architecture & Layering

The project strictly follows **Clean Architecture**, split across a modularized structure to enforce boundaries between layers.



### 1. Presentation Layer (`app` module)
This layer is responsible for everything the user sees and interacts with. It relies entirely on **Jetpack Compose** for a modern, declarative UI.

* **Screens:**
  * `NewsListScreen` – Displays breaking news with swipe-to-refresh capabilities.
  * `ArticleDetailsScreen` – Shows the full content of a selected article.
* **UI Components:** `NewsCard`, `NewsImage` (Optimized image loading).
* **Navigation:** Type-safe navigation handled via `Navigation.kt` and `Route.kt`.
* **State Management:** `NewsViewModel` utilizes Kotlin `StateFlow` to emit UI states safely across lifecycle changes.

### 2. Domain Layer (`model` module → domain)
The core business logic of the application. It is completely isolated from UI frameworks and data sources (pure Kotlin).

* **Entities:** `Article` (The core business model).
* **Repository Interface:** `NewsRepository` (Defines the contract for data operations).
* **Use Cases:**
  * `GetBreakingNewsUseCase`
  * `RefreshNewsUseCase`
  * `DeleteArticleUseCase`

### 3. Data Layer (`model` module → data)
Manages data fetching, saving, and syncing strategies.

* **📡 Remote Data Source:** `NewsApiService` (Retrofit interface) & `RetrofitClient` mapping API responses into Data Transfer Objects (`dto.kt`).
* **💾 Local Data Source:** Room implementation consisting of `NewsDatabase`, `ArticleDao`, and `ArticleEntity`.
* **🔁 Repository Implementation:** `NewsRepoImpl` acts as the Single Source of Truth. It orchestrates network requests and database updates, serving cached data when network connectivity is unavailable.

---

## 🔄 Data Flow

The application utilizes a unidirectional reactive data flow:

[Remote API (Retrofit)]
│
▼
[NewsRepoImpl (Repository)] ──(Saves to)──► [Room Database (Cache)]
│                                            │
▼                                            ▼
[Use Cases (Domain)] ◄────────────────────────(Observes via Flow)
│
▼
[NewsViewModel (UI StateFlow)]
│
▼
[Jetpack Compose UI]


---

## ✨ Features

* **Breaking News:** Fetches the most recent global articles instantly.
* **Offline Caching:** Full offline support; browse previously loaded news anytime.
* **Manual Refresh:** Pull-to-refresh mechanism to force-update the local cache.
* **Article Management:** Ability to delete specific cached articles.
* **Reactive UI:** Powered entirely by Kotlin Coroutines and asynchronous Flows.
* **Multi-Module Design:** Decoupled codebase divided into `app` and `model` modules.

---

## 📦 Multi-Module Structure

| Module | Core Responsibility | Key Technologies |
| :--- | :--- | :--- |
| **`:app`** | Presentation, UI, ViewModels, and App Configuration | Jetpack Compose, Navigation |
| **`:model`** | Domain (Business Logic) & Data (Network + DB) | Room, Retrofit, Coroutines |

### 📂 Directory Tree

```text
app/
 └── ui/
     ├── component/       # Reusable Compose components (NewsCard, etc.)
     ├── navigation/      # Navigation graphs and destinations
     ├── screens/         # Full-screen Composables (List & Details)
     ├── theme/           # Color schemes, Typography, and Shapes
     └── view_model/      # Architecture ViewModels & Factories

model/
 ├── data/
 │   ├── local/          # Room DB, Entity definitions, and DAOs
 │   ├── remote/         # Retrofit interfaces, Client, and DTOs
 │   └── repo/           # Repository implementations (NewsRepoImpl)
 └── domain/
     ├── entities/       # Pure Kotlin business models
     ├── repo/           # Repository abstractions
     └── use_cases/      # Single-responsibility business actions

🛠️ Tech Stack & Libraries

    Language: Kotlin (100% Type-safe & Modern)

    UI Framework: Jetpack Compose

    Asynchronous Programming: Kotlin Coroutines & Asynchronous Flow

    Networking: Retrofit & OkHttp

    Local Storage: Room Database

    Architecture: MVVM + Clean Architecture (Domain-Driven Design principles)

🎯 Project Goals

This repository is built as a reference architecture to demonstrate:

    Real-world implementation of Clean Architecture in modular Android apps.

    Handling robust data synchronization between Network (Retrofit) and Local Cache (Room).

    Implementing clean, lifecycle-aware UI state management using Jetpack Compose and Coroutines.

🤝 Contributing

Contributions are welcome! If you want to enhance this project (e.g., adding Pagination, Search capabilities, or Dependency Injection with Hilt), feel free to open an issue or submit a pull request.
📄 License

This project is open-source and available for educational and training purposes.
