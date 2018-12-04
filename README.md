# Smart_Mirror
Computer Science Senior Project: The Smart Mirror uses voice commands and color tracking for controls.
The Smart mirror has multiple features and will have more in the future. 
The available features
<p>YouTube - The ability to search video</p>
<p>Weather - Show the weather based on city</p>
<p>Time and Date - Shows the time date base on city</p>
<p>Twitter - The ability to create and view post</p>

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites
You will have to provide your own keys for the Smart Mirror.

 JavaFX: If you do not have it downloaded, follow this tutorial [here](https://www.eclipse.org/efxclipse/install.html)
<ul>Twitter Consumer Keys and Access Token & Access Token Secret</ul>
<ul>YouTube Data API v3 OAuth 2.0 client IDs (Json File)</ul>
<ul>OpenWeatherMap API key</ul>
<ul>OpenCV</ul>


### Installing
Clone the project from the repository to your workspace.

then go this website and follow the instruction
You should download the OpenCV library (version 3.x) from [here](https://opencv.org/releases.html).
```
https://opencv.org/releases.html
```
Then, extract the downloaded OpenCV file in a location of your choice. Once you get the folder opencv put in wherever you prefer.

Now the only two things that you will need are: the opencv-3xx.jar file located at **\opencv\build\java** and the **opencv_java3xx.dll** library located at **\opencv\build\java\x64 (for 64-bit systems)** or **\opencv\build\java\x86 (for 32-bit systems)**. The 3xx suffix of each file is a shortcut for the current OpenCV version, e.g., it will be 300 for OpenCV 3.0 and 330 for OpenCV 3.3.

This tutorial for Opencv maybe useful:
```
https://opencv-java-tutorials.readthedocs.io/en/latest/02-first-java-application-with-opencv.html
```


### Runing the application
On the settings menu there are two preset options for detecting Colors which are **Blue** and **Green**.
You can start the camera by itself by pressing the **camera button**.
You can start the voice commands by pressing the **voice command buttom**.
To start the application, press the **continue** button.

**YOU MUST** press **continue** only when starting the program to run the mirror application. The voice and color tracking will start on its own.
If you want to test voice and color tracking use the **camera button** and **voice command button**:
Start the Voice commands first
Then Start the Camera.

## Controls
**Color Tracking**
UP Motion   - Select current highlighted feature
DOWN Motion - Confirm action, such as posting tweet or searching for youTube video
RIGHT Motion - Move to next Icon
LEFT Motion - Move to previous Icon

**Voice Commands**
Voice commands are In the grammer file. [here](https://github.com/TyreKing/Smart_Mirror/blob/master/capstone/src/application/grammar.gram).

mirror select | mirror choose | mirror right | mirror left | mirror stop listening|
mirror up | mirror down | mirror open twitter | mirror write tweet | mirror post tweet |
mirror post tweet | mirror cancel | mirror confirm | mirror sleep | mirror search |
mirror mirror | mirror play | mirror pause | mirror sleep | mirror open youtube |
mirror show twitter feed | mirror open twitter | mirror create tweet | mirror show time | mirror show weather

## Acknowledgments
OpenCV JavaTutorial
OpenWeatherMap.org
