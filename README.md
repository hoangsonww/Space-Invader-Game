# Space Shooter Game JavaFX

## Overview
Space Shooter is a simple, lightweight but engaging JavaFX-based game where players navigate a spaceship, avoiding enemies and obstacles while trying to achieve the highest score possible. The game incorporates features such as enemy spawn logic, collision detection, power-ups, and a scoring system, showcasing the capabilities of JavaFX in creating interactive 2D games.

## User Interface

### Start Screen

<p align="center" style="cursor: pointer">
    <img src="start.png" alt="The WeatherMate App Interface" width="100%"/>
</p>

### Gameplay

<p align="center" style="cursor: pointer">
    <img src="gameplay.png" alt="The WeatherMate App Interface" width="100%"/>
</p>

### Losing Screen

<p align="center" style="cursor: pointer">
    <img src="losing.png" alt="The WeatherMate App Interface" width="100%"/>
</p>

## Features
- Control a spaceship using keyboard inputs (A, W, S, D, or arrow keys) to move and SPACE to shoot.
- Enemies and boss enemies spawn at intervals, increasing the game's difficulty.
- Collect power-ups to boost your abilities.
- Score tracking and display, with the game increasing in difficulty as your score rises.
- Lives system where players lose a life if an enemy reaches the bottom of the screen or collides with the player's spaceship.
- Reset mechanism to start over once all lives are lost.

## Prerequisites
- JDK 11 or higher.
- JavaFX SDK (version compatible with your JDK).

## Setup and Installation
1. **JavaFX SDK**: Download the JavaFX SDK from [OpenJFX](https://openjfx.io/) and extract it to a known directory.
2. **Clone the Repository**: Use `git clone <repository-url>` to clone this repository to your local machine.

## Configuration
Ensure JavaFX is correctly set up in your development environment:
- **IntelliJ IDEA**:
  1. Go to `File` > `Project Structure` > `Libraries`, add the JavaFX SDK as a library by navigating to its `lib` folder.
  2. Modify your run configuration to include VM options for the JavaFX modules you use, e.g., `--module-path "path/to/javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml,javafx.media`.

- **Eclipse**:
  1. Add the JavaFX SDK as an external JAR to your project's build path.
  2. Adjust the run configurations to include the necessary VM arguments, as shown above for IntelliJ.

## Running the Application
Compile and run the `SpaceShooter.java` file. Ensure the VM options are set correctly to include the JavaFX library path and modules.

## How to Play
- Use the A, W, S, D keys or arrow keys to move the spaceship.
- Press SPACE to shoot at enemies.
- Avoid letting enemies reach the bottom of the screen or colliding with them.
- Collect power-ups to enhance your capabilities and increase your score.
- The game ends when all lives are lost, but you can start over by resetting the game.

## Contributing
Contributions to the Space Shooter game are welcome! Please fork the repository, make your changes, and submit a pull request with your improvements.

## License
This project is licensed under the Creative Commons License.

---

Created with ❤️ by [Son Nguyen](https://github.com/hoangsonww) in 2024.
