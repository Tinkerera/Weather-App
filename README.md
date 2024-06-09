# WeatherApp

WeatherApp is a simple Java Swing GUI application that displays current weather data by entering a city name. This application uses the Open-Meteo API to retrieve weather data and visually presents information such as temperature, weather conditions, humidity, and wind speed.

## Features

- Retrieve weather data by entering a city name
- Display temperature, weather conditions, humidity, and wind speed
- Show appropriate weather icons based on the weather conditions

## Requirements

- Java 8 or higher
- JSON Simple library (json-simple-1.1.1.jar)

## Installation

1. Clone or download this project:

    ```bash
    git clone https://github.com/username/weather-app.git
    ```

2. Download the JSON Simple library and add it to your project configuration. You can download the library from [here](https://code.google.com/archive/p/json-simple/downloads).

3. Open the project in an IDE (IntelliJ, Eclipse, etc.) or follow the steps below to compile and run it via the command line.

## Compilation and Running

### Command Line

1. Compile the project in the project directory with the following commands:

    ```bash
    javac -cp .;path/to/json-simple-1.1.1.jar src/*.java
    ```

2. Run the project:

    ```bash
    java -cp .;path/to/json-simple-1.1.1.jar src.WeatherAppGui
    ```

### IDE

1. Open the project in an IDE.
2. Add the `json-simple-1.1.1.jar` library to the project configuration.
3. Run the `WeatherAppGui` class.

## Usage

1. Run the application.
2. Enter a city name in the search box and click the search button.
3. Weather data will be displayed on the screen.
