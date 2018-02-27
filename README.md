# Memories
***The StoryLab at Texas A&M Android Wear Application***

### Purpose
The purpose of this application is to create an interface to record day-to-day experiences on a wearable device. This program can then be modified based on the scope of the study for future potential publications. 

- - - -
### Layout
* Prompt
* Audio Record
* Survey - First question
* Survey - Second question

- - - -
### Class Initialization
The application loads initially into the MainActivity class, displaying the first screen where the user is prompted to begin recording.
- - - -
### Recording Notes
To compensate Android Wear from being unable to record using the general Android recording class, the MessageRecord class contains code to record an audio stream into a .PCM file. This .PCM file is then given a header, translated into a .WAV file, and the .PCM is then deleted.

- - - -
### Other Classes

#### FirstQuestion
Displays the first question of the survey.

* How do you feel about this event?

#### Second Question
Displays the second question of the survey.

* How satisfied are you with your life as a whole?

#### Answers to the survey questions
The user answers the survey with the help of a four-point Likert scale.
* Extremely Satisfied
* Satisfied
* Dissatisfied
* Extremely Dissatisfied

- - - -
### To run
Download this repository directly to Android Studio. Can either run via the debugger or create a .APK file to install via scripts.
