# MyAdvancedApp

MyAdvancedApp is a comprehensive Android application designed to demonstrate modern Android development practices. It features data fetching from a public API, local offline storage using SQLite, and a rich user interface with theme support.

## 🚀 Features

*   **User Authentication**: Simple mock login system with "Remember Login" functionality.
*   **Network Integration**: Fetches user data from [JSONPlaceholder](https://jsonplaceholder.typicode.com/) using Retrofit.
*   **Offline Persistence**: Caches fetched data locally in an SQLite database for offline access.
*   **Dynamic Theming**:
    *   Supports Light, Dark, and Custom themes.
    *   Themes can be toggled via the Options Menu and are persisted using `SharedPreferences`.
*   **Rich UI Components**:
    *   **RecyclerView**: Displays user lists with custom adapters.
    *   **WebView**: Opens user websites directly within the app.
    *   **Menus**: Implements Options Menu, Context Menu (on long press), and Popup Menus (on list items).
*   **Lifecycle Awareness**: Handles screen rotation and app restarts gracefully.

## 🛠 Tech Stack

*   **Language**: Kotlin
*   **Networking**: [Retrofit 2](https://square.github.io/retrofit/) & [Gson](https://github.com/google/gson)
*   **UI Components**:
    *   AndroidX AppCompat & Core KTX
    *   ConstraintLayout
    *   SwipeRefreshLayout
    *   Google Material Design Components
*   **Database**: SQLite (Native Android implementation)

## 📱 Architecture & Data

### API Integration
*   **Base URL**: `https://jsonplaceholder.typicode.com/`
*   **Endpoint**: `/users` (GET)
*   **Response**: JSON array of user objects.

### Database Schema
The app uses a local SQLite database `MyAdvancedApp.db` to store user information.

**Table**: `users`

| Column   | Type    | Description          |
| :------- | :------ | :------------------- |
| `id`     | INTEGER | Primary Key          |
| `name`   | TEXT    | Full name of the user|
| `username`| TEXT   | User's handle        |
| `email`  | TEXT    | Email address        |
| `website`| TEXT    | Website URL          |

## 📖 Usage Instructions

1.  **Login**: Upon launching the app, enter any non-empty username and password to log in.
2.  **View Users**: The main screen displays a list of users. If internet is available, data is fetched from the API; otherwise, it loads from the local database.
3.  **Quick Actions**:
    *   **Tap**: Opens the user's website in an internal WebView.
    *   **Long Press**: Opens a context menu to **Edit** or **Delete** the user locally.
    *   **3-Dot Icon**: Tap the icon on a user card to view **Info** or **Share** the profile.
4.  **Switch Themes**: Use the top-right menu to switch between Light, Dark, and Custom themes.
5.  **Logout**: Accessible via the top-right menu.

## 🔧 Setup

1.  Clone the repository.
2.  Open the project in **Android Studio**.
3.  Sync Gradle project.
4.  Run the application on an Emulator or Physical Device.

## 📄 License

This project is open source and available for educational purposes.
