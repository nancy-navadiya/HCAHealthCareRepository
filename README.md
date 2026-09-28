
![search_username](https://github.com/user-attachments/assets/b84a416f-07d5-488a-8257-f343541618bf)
![search_and_filter_repository](https://github.com/user-attachments/assets/fa096320-5c32-45dd-bb4e-3e5d477cbb07)
![repo_list](https://github.com/user-attachments/assets/6fd8ca91-96d0-4d47-8963-3b67a0f4fbd3)
![repo_filter_rust](https://github.com/user-attachments/assets/1bce7d91-29eb-49b6-96af-9a6f7c8fcffa)
![repo_filter_javascript](https://github.com/user-attachments/assets/2b30b707-d09d-414c-a2eb-5a9d69fb5d23)
![repo_detail](https://github.com/user-attachments/assets/c62df373-3943-4736-8283-10cae0f2d308)

# GitHub Repositories Explorer

An Android application that allows users to explore GitHub repositories for a given user.
The app displays repository details such as avatar, name, description, language, stars, forks, and some additional repository information.

## Features

1. List of Repositories:
- Fetches and displays a list of repositories for a specified user from the GitHub API.
- Shows key information such as:
- Repository name
- Description
- Language
- Number of stars
- Number of forks

2. Filtering:
- Allows users to filter repositories by programming language (e.g., Kotlin, Java, PHP, JavaScript, HTML).

3. Repository Details:
- Displays detailed information when a repository is selected, such as:
- Owner information (name, avatar, type of bussines)
- Description
- Last updated date
- Default Branch

4. Pagination:
- Loads more repositories as the user scrolls to the bottom of the list.

5. Error Handling:
- Handled API
- Displays error messages when necessary.

## Technologies Used

- Kotlin: Primary language for Android development.
- MVVM (Model-View-ViewModel): Implements separation of concerns and testability.
- Jetpack Compose: Declarative UI for the repository list, filtering, and detail screens.
- Navigation Compose: Manages navigation between repository list and detail screens.
- Hilt: Provides the Retrofit API and repository dependencies.
- Retrofit: For making network calls to GitHub API.
- Kotlin Coroutines and Flow: Handles asynchronous work and UI state.
- Paging 3: Loads repositories incrementally as the user scrolls.
- Unit Testing: Provides test coverage for ViewModel and repository logic.

## Getting Started

### Prerequisites

- Android Studio
- Android SDK (version 24 or higher)
- Git (to clone the repository)

### Installation

1. Clone the repository:

```bash![Uploading repo_detail.png…]()

git clone https://github.com/your-username/your-repository-name.git
