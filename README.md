# Rutube1 Android App

An Android application that plays YouTube videos and provides real-time Russian translation of English speech.

## Project Setup

### Requirements
- Android Studio Arctic Fox (2021.3.1) or newer
- Android SDK API Level 33 (Android 13.0)
- Minimum SDK: 24 (Android 7.0)
- JDK 11
- Kotlin 1.8.0

### Import Instructions
1. Open Android Studio
2. Select "Open an Existing Project"
3. Navigate to and select this project's root directory
4. Wait for Android Studio to sync and configure the project
5. Accept any prompts to install missing SDK components

### Features
- YouTube video playback using ExoPlayer
- Real-time English speech recognition
- English to Russian translation using ML Kit
- Display of translated subtitles in real-time

### Project Structure
```
app/src/main/
├── java/com/example/rutube1/
│   ├── MainActivity.kt           # Main activity handling UI and user interactions
│   ├── TranslationManager.kt     # Manages speech recognition and translation
│   └── VideoManager.kt          # Handles video loading and playback
└── res/
    ├── layout/
    │   └── activity_main.xml    # Main UI layout
    └── values/
        └── strings.xml         # String resources
```

### Implementation Details
The app uses:
- ExoPlayer for video playback
- Google ML Kit for translation
- Android Speech Recognition for speech-to-text
- AndroidX components for UI

### Building and Running
1. Sync project with Gradle files
2. Build the project (Build > Make Project)
3. Run on an emulator or physical device
4. Enter a YouTube video URL
5. Click "Translate Video" button
6. Grant necessary permissions when prompted

### Permissions Required
- Internet access for video streaming
- Microphone access for speech recognition

### Testing
To test the app:
1. Enter a valid YouTube video URL
2. Click "Translate Video" button
3. Allow microphone permissions when prompted
4. The video will play with real-time Russian translations

### Troubleshooting
If you encounter build issues:
1. Ensure Android SDK is properly configured in Android Studio
2. Verify that all required SDK components are installed
3. Check that JDK 11 is selected in Project Structure
4. Sync project with Gradle files after any configuration changes

For runtime issues:
1. Check logcat for detailed error messages
2. Verify internet connectivity for video streaming
3. Ensure microphone permissions are granted
4. Validate that the YouTube URL is correctly formatted