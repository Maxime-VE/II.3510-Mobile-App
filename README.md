# HouseNote

HouseNote is a simple Android app for creating and managing notes. It supports features like changing note color, marking notes as finished, and deleting notes.

## Table of Contents

### - Installation
### - Features
### -Code Overview
### - Dependencies

## Installation

To run the app:

1. Clone the repository:
   ```bash
   git clone https://github.com/Maxime-VE/II.3510-Mobile-App
   git checkout Java-Project
   ```
2. Open the project in Android Studio.
3. Build and run the app on an emulator or Android device (API 31-35).

## Features

- **Add Note**: Create a new note.
- **Edit Note**: Update an existing note.
- **Delete Note**: Remove a note.
- **Change Note Color**: Set a background color for a note.
- **Mark Note as Finished**: Strike through completed notes.

## Code Overview

- **MainActivity**: Displays the list of notes.
- **FormActivity**: Handles adding and editing notes.
- **Notes Model**: Represents a note with properties like content, user, date, color, and status.

### Note Model
The core of the app is the ```Notes``` model:
```java
public class Notes extends RealmObject {
    @PrimaryKey
    private String id;
    String mContenu;
    String mUser;
    long mDate;
    private int noteColor;
    boolean isFinished;

    // Getters and setters...
}
```

## Dependencies

- Realm: A local database for storing notes.
- Android Support Libraries: Core components for the app.


