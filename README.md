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

A step by step series of examples that tell you how to get a development env running

Say what the step will be

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

**YOU MUST** press **continue** only when starting the program. The voice and color tracking will start with the program.
If you want to test voice and color tracking use the **camera button** and **voice command button**.





## Built With

* [OpenWeatherMap](https://openweathermap.org/) - Used to gather Weather
* [Maven](https://maven.apache.org/) - Dependency Management
* [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds


## Acknowledgments

OpenCV JavaTutorial
OpenWeatherMap.org
