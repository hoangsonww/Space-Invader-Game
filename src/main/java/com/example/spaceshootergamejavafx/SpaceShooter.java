package com.example.spaceshootergamejavafx;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.text.TextAlignment;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Objects;

public class SpaceShooter extends Application {

  public static final int WIDTH = 350;

  public static final int HEIGHT = 800;

  public static int numLives = 3;

  private int score = 0;

  private boolean bossExists = false;

  private boolean reset = false;

  private final Label scoreLabel = new Label("Score: " + score);

  private final Label lifeLabel = new Label("Lives: " + numLives);

  private final List<GameObject> gameObjects = new ArrayList<>();

  private final List<GameObject> newObjects = new ArrayList<>();

  private Player player = new Player(WIDTH / 2, HEIGHT - 40);

  private Pane root = new Pane();

  private Scene scene = new Scene(root, WIDTH, HEIGHT, Color.BLACK);

  private boolean levelUpMessageDisplayed = false;

  private boolean levelUpShown = false;

  private Stage primaryStage;

  private boolean gameRunning = false;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    this.primaryStage = primaryStage;
    primaryStage.setScene(scene);
    primaryStage.setTitle("Space Shooter");
    primaryStage.setResizable(false);

    primaryStage.getIcons().add(new Image(Objects.requireNonNull(
      getClass().getResourceAsStream("/player.png"))));

    Canvas canvas = new Canvas(WIDTH, HEIGHT);
    scoreLabel.setTranslateX(10);
    scoreLabel.setTranslateY(10);
    scoreLabel.setTextFill(Color.BLACK);
    scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
    root.getChildren().addAll(canvas, scoreLabel, lifeLabel);
    lifeLabel.setTranslateX(10);
    lifeLabel.setTranslateY(40);
    lifeLabel.setTextFill(Color.BLACK);
    lifeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
    GraphicsContext gc = canvas.getGraphicsContext2D();
    gameObjects.add(player);
    Pane menuPane = createMenu();
    Scene menuScene = new Scene(menuPane, WIDTH, HEIGHT);
    primaryStage.setScene(menuScene);
    primaryStage.setTitle("Space Shooter");
    primaryStage.setResizable(false);
    initEventHandlers(scene);

    AnimationTimer gameLoop =
        new AnimationTimer() {
          private long lastEnemySpawned = 0;

          private long lastPowerUpSpawned = 0;

          @Override
          public void handle(long now) {
            if (!gameRunning) return;

            if (reset) {
              this.start();
              reset = false;
            }
            gc.clearRect(0, 0, WIDTH, HEIGHT);

            if (now - lastEnemySpawned > 1_000_000_000) {
              spawnEnemy();
              lastEnemySpawned = now;
            }

            if (now - lastPowerUpSpawned > 10_000_000_000L) {
              spawnPowerUp();
              lastPowerUpSpawned = now;
            }

            if (score >= 200 && score % 200 == 0) {
              boolean bossExists = false;
              for (GameObject obj : gameObjects) {
                if (obj instanceof BossEnemy) {
                  bossExists = true;
                  break;
                }
              }
              if (!bossExists) {
                spawnBossEnemy();
              }
            }

            checkCollisions();
            checkEnemiesReachingBottom();

            gameObjects.addAll(newObjects);
            newObjects.clear();

            for (GameObject obj : gameObjects) {
              obj.update();
              obj.render(gc);
            }

            Iterator<GameObject> iterator = gameObjects.iterator();
            while (iterator.hasNext()) {
              GameObject obj = iterator.next();
              if (obj.isDead()) {
                iterator.remove();
              }
            }
          }
        };

    gameLoop.start();

    primaryStage.show();
  }

  private void spawnEnemy() {
    Random random = new Random();
    int x = random.nextInt(WIDTH - 50) + 25;

    if (score % 200 == 0 && score > 0 && !bossExists) {
      BossEnemy boss = new BossEnemy(x, -50);
      gameObjects.add(boss);
      showTempMessage("A boss is ahead, watch out!", 75, HEIGHT / 2 - 100, 5);
      bossExists = true; // Ensure we don't spawn multiple bosses
    } else {
      Enemy enemy = new Enemy(x, -40);
      gameObjects.add(enemy);
    }
  }

  private void checkCollisions() {
    List<Bullet> bullets = new ArrayList<>();
    List<Enemy> enemies = new ArrayList<>();
    List<PowerUp> powerUps = new ArrayList<>();

    for (GameObject obj : gameObjects) {
      if (obj instanceof Bullet) {
        bullets.add((Bullet) obj);
      } else if (obj instanceof Enemy) {
        enemies.add((Enemy) obj);
      } else if (obj instanceof PowerUp) {
        powerUps.add((PowerUp) obj);
      }
    }

    for (Bullet bullet : bullets) {
      for (Enemy enemy : enemies) {
        if (bullet.getBounds().intersects(enemy.getBounds())) {
          bullet.setDead(true);
          if (enemy instanceof BossEnemy) {
            ((BossEnemy) enemy).takeDamage();
            score += 20;
          } else {
            enemy.setDead(true);
            score += 10;
          }
          scoreLabel.setText("Score: " + score);

          if (score % 100 == 0) {
            Enemy.SPEED += 0.4;
          }
        }
      }

      // Check collisions between bullets and power-ups
      for (PowerUp powerUp : powerUps) {
        if (bullet.getBounds().intersects(powerUp.getBounds())) {
          bullet.setDead(true);
          powerUp.setDead(true);
          score += 50;
          scoreLabel.setText("Score: " + score);
        }
      }
    }

    if (score % 100 == 0 && score > 0 && !levelUpShown) {
      showTempMessage("Level Up!", 135, HEIGHT / 2, 2);
      levelUpShown = true;
    } else if (score % 100 != 0) {
      levelUpShown = false;
    }
  }

  private void checkEnemiesReachingBottom() {
    List<Enemy> enemies = new ArrayList<>();
    for (GameObject obj : gameObjects) {
      if (obj instanceof Enemy) {
        enemies.add((Enemy) obj);
      }
    }

    for (Enemy enemy : enemies) {
      if (enemy.getY() + enemy.getHeight() / 2 >= HEIGHT) {
        enemy.setDead(true);
        enemy.SPEED = enemy.SPEED + 0.4;
        numLives--;
        score -= 10;
        lifeLabel.setText("Lives: " + numLives);
        if (numLives < 0) {
          resetGame();
        }
      }
    }
  }

  private void showLosingScreen() {
    Pane losingPane = new Pane();
    losingPane.setStyle("-fx-background-color: black;");

    // Game Over Text
    Text gameOverText = new Text("GAME OVER");
    gameOverText.setFont(Font.font("Arial", FontWeight.BOLD, 40));
    gameOverText.setFill(Color.RED);
    gameOverText.setX((WIDTH - gameOverText.getLayoutBounds().getWidth()) / 2);
    gameOverText.setY(150);

    // Score Display
    if (score < 0) {
      score = 0;
    }

    Text scoreText = new Text("Your Score: " + score);
    scoreText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
    scoreText.setFill(Color.WHITE);
    scoreText.setX((WIDTH - scoreText.getLayoutBounds().getWidth()) / 2);
    scoreText.setY(250);

    // Try Again Button
    Button tryAgainButton = new Button("Try Again");
    tryAgainButton.setStyle(
        "-fx-background-color: #444; -fx-text-fill: white; -fx-font-size: 18; "
            + "-fx-font-weight: bold; -fx-padding: 10 20;");
    tryAgainButton.setOnMouseEntered(
        event -> {
          tryAgainButton.setStyle(
              "-fx-background-color: white; -fx-text-fill: black; -fx-font-size: 18; "
                  + "-fx-font-weight: bold; -fx-padding: 10 20;");
          tryAgainButton.setEffect(new Glow(0.5));
        });
    tryAgainButton.setOnMouseExited(
        event -> {
          tryAgainButton.setStyle(
              "-fx-background-color: #444; -fx-text-fill: white; -fx-font-size: 18; "
                  + "-fx-font-weight: bold; -fx-padding: 10 20;");
          tryAgainButton.setEffect(null);
        });
    tryAgainButton.setLayoutX(120);
    tryAgainButton.setLayoutY(350);
    tryAgainButton.setOnAction(event -> restartGame());

    // Exit Button
    Button exitButton = new Button("Exit Game");
    exitButton.setStyle(
        "-fx-background-color: #d9534f; -fx-text-fill: white; -fx-font-size: 18; "
            + "-fx-font-weight: bold; -fx-padding: 10 20;");
    exitButton.setOnMouseEntered(
        event -> {
          exitButton.setStyle(
              "-fx-background-color: white; -fx-text-fill: red; -fx-font-size: 18; "
                  + "-fx-font-weight: bold; -fx-padding: 10 20;");
          exitButton.setEffect(new Glow(0.5));
        });
    exitButton.setOnMouseExited(
        event -> {
          exitButton.setStyle(
              "-fx-background-color: #d9534f; -fx-text-fill: white; -fx-font-size: 18; "
                  + "-fx-font-weight: bold; -fx-padding: 10 20;");
          exitButton.setEffect(null);
        });
    exitButton.setLayoutX(120);
    exitButton.setLayoutY(450);
    exitButton.setOnAction(event -> System.exit(0));

    losingPane.getChildren().addAll(gameOverText, scoreText, tryAgainButton, exitButton);

    // Create and set the losing screen scene
    Scene losingScene = new Scene(losingPane, WIDTH, HEIGHT);
    primaryStage.setScene(losingScene);
  }

  private void restartGame() {
    gameObjects.clear();
    numLives = 3;
    score = 0;
    lifeLabel.setText("Lives: " + numLives);
    scoreLabel.setText("Score: " + score);
    gameObjects.add(player);
    reset = true;
    gameRunning = true;
    primaryStage.setScene(scene); // Return to the main game scene
  }

  private void resetGame() {
    gameRunning = false;
    showLosingScreen();
  }

  private void initEventHandlers(Scene scene) {
    scene.setOnKeyPressed(
        event -> {
          switch (event.getCode()) {
            case A:
            case LEFT:
              player.setMoveLeft(true);
              break;
            case D:
            case RIGHT:
              player.setMoveRight(true);
              break;
            case S:
            case DOWN:
              player.setMoveBackward(true);
              break;
            case W:
            case UP:
              player.setMoveForward(true);
              break;
            case SPACE:
              player.shoot(newObjects);
              break;
          }
        });

    scene.setOnKeyReleased(
        event -> {
          switch (event.getCode()) {
            case A:
            case LEFT:
              player.setMoveLeft(false);
              break;
            case D:
            case RIGHT:
              player.setMoveRight(false);
              break;
            case S:
            case DOWN:
              player.setMoveBackward(false);
              break;
            case W:
            case UP:
              player.setMoveForward(false);
              break;
          }
        });
  }

  private void spawnPowerUp() {
    Random random = new Random();
    int x = random.nextInt(WIDTH - PowerUp.WIDTH) + PowerUp.WIDTH / 2;
    PowerUp powerUp = new PowerUp(x, -PowerUp.HEIGHT / 2);
    gameObjects.add(powerUp);
  }

  private void spawnBossEnemy() {
    if (gameObjects.stream().noneMatch(obj -> obj instanceof BossEnemy)) {
      BossEnemy bossEnemy = new BossEnemy(WIDTH / 2, -40);
      gameObjects.add(bossEnemy);
    }
  }

  private Pane createMenu() {
    Pane menuPane = new Pane();
    menuPane.setStyle("-fx-background-color: linear-gradient(to bottom, #1e3c72, #2a5298);"); // Gradient background

    // Styled title
    Text welcomeText = new Text("Welcome to\nSpace Shooter!");
    welcomeText.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 36)); // Bold and larger font
    welcomeText.setFill(Color.LIGHTCYAN); // Softer text color
    welcomeText.setEffect(new DropShadow(10, Color.CYAN)); // Add a shadow effect
    welcomeText.setTextAlignment(TextAlignment.CENTER);
    welcomeText.setX(WIDTH / 2 - 150);
    welcomeText.setY(100);

    // Styled buttons
    Button startButton = createStyledButton("START", 200);
    startButton.setOnAction(event -> startGame());

    Button instructionsButton = createStyledButton("INSTRUCTIONS", 300);
    instructionsButton.setOnAction(event -> showInstructions());

    Button quitButton = createStyledButton("QUIT", 400);
    quitButton.setOnAction(event -> System.exit(0));

    // Button layout container
    VBox buttonsContainer = new VBox(20);
    buttonsContainer.setLayoutX(WIDTH / 2 - 75); // Center the buttons
    buttonsContainer.setLayoutY(200);
    buttonsContainer.getChildren().addAll(startButton, instructionsButton, quitButton);

    menuPane.getChildren().addAll(welcomeText, buttonsContainer);

    return menuPane;
  }

  // Method to style individual buttons
  private Button createStyledButton(String text, double y) {
    Button button = new Button(text);
    button.setStyle(
      "-fx-background-color: linear-gradient(to right, #6a11cb, #2575fc);"
        + "-fx-text-fill: white;"
        + "-fx-font-size: 18;"
        + "-fx-font-weight: bold;"
        + "-fx-padding: 10 20;"
        + "-fx-border-radius: 20;"
        + "-fx-background-radius: 20;"
        + "-fx-border-color: #ffffff;"
        + "-fx-border-width: 2;");
    button.setOnMouseEntered(event -> {
      button.setStyle(
        "-fx-background-color: linear-gradient(to right, #2575fc, #6a11cb);"
          + "-fx-text-fill: yellow;"
          + "-fx-font-size: 18;"
          + "-fx-font-weight: bold;"
          + "-fx-padding: 10 20;"
          + "-fx-border-radius: 20;"
          + "-fx-background-radius: 20;"
          + "-fx-border-color: yellow;"
          + "-fx-border-width: 2;");
      button.setEffect(new Glow(0.5));
    });
    button.setOnMouseExited(event -> {
      button.setStyle(
        "-fx-background-color: linear-gradient(to right, #6a11cb, #2575fc);"
          + "-fx-text-fill: white;"
          + "-fx-font-size: 18;"
          + "-fx-font-weight: bold;"
          + "-fx-padding: 10 20;"
          + "-fx-border-radius: 20;"
          + "-fx-background-radius: 20;"
          + "-fx-border-color: #ffffff;"
          + "-fx-border-width: 2;");
      button.setEffect(null);
    });
    return button;
  }

  private void showInstructions() {
    Alert instructionsAlert = new Alert(AlertType.INFORMATION);
    instructionsAlert.setTitle("Instructions");
    instructionsAlert.setHeaderText("Space Shooter Instructions");
    instructionsAlert.setContentText(
        "Use the A, W, S, and D keys or the arrow keys to move your spaceship.\n"
            + "Press SPACE to shoot bullets and destroy the enemies.\n"
            + "If an enemy reaches the bottom of the screen, you lose a life.\n"
            + "The game resets if you lose all lives.\n"
            + "Collect power-ups to increase your score.\n"
            + "Defeat the boss enemy to level up and increase the difficulty.\n"
            + "Good luck and have fun!");
    instructionsAlert.showAndWait();
  }

  private void showTempMessage(String message, double x, double y, double duration) {
    Text tempMessage = new Text(message);
    tempMessage.setFont(Font.font("Arial", FontWeight.BOLD, 18));
    tempMessage.setFill(Color.RED);
    tempMessage.setX(x);
    tempMessage.setY(y);
    root.getChildren().add(tempMessage);

    PauseTransition pause = new PauseTransition(Duration.seconds(duration));
    pause.setOnFinished(event -> root.getChildren().remove(tempMessage));
    pause.play();
  }

  private void startGame() {
    gameRunning = true;
    primaryStage.setScene(scene);
  }
}
