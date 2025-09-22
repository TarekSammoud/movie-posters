MoviePosters
MoviePosters is an Android application built with Kotlin and Jetpack Compose that allows users to search for movies using the OMDB API, display their posters and details, and share movie information (including posters) via various apps.
Features

Search Movies: Enter a movie title to fetch details and posters from the OMDB API.
Display Movie Details: View movie title, director, release date, genre, and poster.
Share Movie Information: Share movie details and posters via apps like WhatsApp, Email, or Messages.
Modern UI: Built with Jetpack Compose for a responsive and modern user interface.
Image Loading: Uses Coil 3.2.0 to efficiently load and display movie posters.
Error Handling: Gracefully handles invalid URLs, network issues, and missing posters.

Technologies Used

Kotlin: Primary programming language.
Jetpack Compose: For building the UI.
Coil 3.2.0: For loading and processing movie poster images.
OMDB API: For fetching movie data and poster URLs.
FileProvider: For sharing poster images securely.
MVVM Architecture: Uses ViewModel for data management.
Coroutines: For asynchronous operations like API calls and image downloading.

Prerequisites

Android Studio: Latest version (e.g., Koala or later).
Kotlin: Version 1.9.0 or higher.
OMDB API Key: Obtain a free API key from OMDB API.
Git: For cloning and managing the repository.
Android Device/Emulator: API 21 (Lollipop) or higher.

Setup Instructions

Clone the Repository:
git clone https://github.com/your-username/MoviePosters.git
cd MoviePosters


Configure OMDB API Key:

Create a file named local.properties in the project root.
Add your OMDB API key:omdb.api.key=your_api_key_here


Replace your_api_key_here with your actual OMDB API key.


Open in Android Studio:

Open Android Studio and select File > Open, then choose the MoviePosters directory.
Sync the project with Gradle by clicking Sync Project with Gradle Files.


Add Dependencies:Ensure the following dependencies are in app/build.gradle:
implementation "io.coil-kt:coil-compose:3.2.0"
implementation "androidx.core:core:1.13.1"
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3"
implementation "androidx.activity:activity-compose:1.9.2"
implementation "androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6"

Sync Gradle after adding dependencies.

Configure FileProvider:

Ensure res/xml/file_provider_paths.xml exists:<?xml version="1.0" encoding="utf-8"?>
<paths xmlns:android="http://schemas.android.com/apk/res/android">
    <cache-path name="images" path="." />
</paths>


Verify AndroidManifest.xml includes:<application>
    <provider
        android:name="androidx.core.content.FileProvider"
        android:authorities="fr.eseo.ld.ts.movieposters.fileprovider"
        android:exported="false"
        android:grantUriPermissions="true">
        <meta-data
            android:name="android.support.FILE_PROVIDER_PATHS"
            android:resource="@xml/file_provider_paths" />
    </provider>
</application>




Build and Run:

Connect an Android device or start an emulator.
Click Run > Run 'app' in Android Studio.



Usage

Search for a Movie:

Enter a movie title (e.g., "Fight Club") in the search bar.
Click the Search button to fetch movie details and poster.


View Movie Details:

The movie poster, title, director, release date, and genre are displayed.


Share Movie Information:

Click the share button in the top app bar.
Choose an app (e.g., WhatsApp, Email) to share the movie title and poster.



Project Structure

app/src/main/java/fr/eseo/ld/ts/movieposters:

MainActivity.kt: Entry point with Jetpack Compose UI.
MovieSearchScreen.kt: Main screen with search bar and movie display.
MovieComposeAppBar.kt: Top app bar with share button.
MoviePosterScreenContent.kt: Displays movie details and poster.
MovieViewModel.kt: Manages movie data using MVVM.
OmdbRepository.kt: Handles OMDB API calls.
models/Movie.kt: Data class for movie details.


app/src/main/res:

xml/file_provider_paths.xml: FileProvider configuration for sharing images.
drawable/ic_launcher_foreground.xml: Placeholder image for posters.


build.gradle: Project and app-level Gradle files with dependencies.


Troubleshooting

Poster Not Loading:

Check Logcat for "COIL" or "SHARE" tags to diagnose errors.
Verify the OMDB API key in local.properties.
Ensure internet connectivity and valid poster URLs.


Share Intent Issues:

Confirm FileProvider is correctly configured in AndroidManifest.xml.
Test sharing with multiple apps (e.g., Email, WhatsApp).


Build Errors:

Sync Gradle and rebuild the project.
Check for missing dependencies or version mismatches.



Contributing

Fork the repository.
Create a feature branch: git checkout -b feature/your-feature.
Commit changes: git commit -m "Add your feature".
Push to the branch: git push origin feature/your-feature.
Open a pull request.

License
This project is licensed under the MIT License. See the LICENSE file for details.
Acknowledgments

OMDB API for providing movie data.
Coil for image loading.
Jetpack Compose for modern Android UI.
