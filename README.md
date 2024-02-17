# JavaFX Projects

Contains four JavaFX applications, which replicate some well-known games including Flappy Bird, Snake, Tetris, and 2048. 

## Getting Started

### Prerequisites

* JDK 21.0.2 (or later)
  
* JavaFX SDK 21.0.2 (must be compatible with the JDK version)
  
* IntelliJ IDEA 2023.3.4 (or later)
  
* Windows 10 (or later) or macOS 12 (or later)

### Installing & Executing Program

Run the JavaFX applications on IntelliJ IDEA. To download and execute the applications, follow these steps:

* Download the zip file from the main branch and extract the javafx-projects-main folder. This folder contains four individual project folders.
  
* Open the desired project on IntelliJ IDEA.
  
* Go to File -> Project Structure -> Project, and set SDK to 21.
  
* Go to File -> Project Structure -> Libraries, and add the lib folder of the JavaFX 21 SDK as a library.
  
* Go to Run -> Edit Configurations, and add new configuration. Set Main class to the class labeled "App" (i.e. FlappyBirdApp, App2048). Add these VM Options to the configuration (replace path with the actual path to JavaFX SDK lib folder):
  
  ```
  --module-path /path/to/javafx-sdk-21.0.2/lib --add-modules javafx.controls,javafx.fxml
  ```
  
* Run the class labeled "App".

## Authors

ycz425 [linkedin.com/in/yuchong-zhang-4a400b2b5]

## License

This project is licensed under the [NAME HERE] License - see the LICENSE.md file for details
