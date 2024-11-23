package com.example.spaceshootergamejavafx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Objects;

/** Represents an enemy object in the game. */
public class Enemy extends GameObject {

  /** Width of the enemy hitbox. */
  protected static final int WIDTH = 30;

  /** Height of the enemy hitbox. */
  protected static final int HEIGHT = 30;

  /** Speed of the enemy. */
  public static double SPEED = 1;

  /** Image for the enemy. */
  private final Image enemyImage;

  /** Flag to indicate if the enemy is dead. */
  private boolean dead = false;

  /**
   * Creates a new enemy object at the specified position.
   *
   * @param x X-coordinate of the enemy.
   * @param y Y-coordinate of the enemy.
   */
  public Enemy(double x, double y) {
    super(x, y, WIDTH, HEIGHT);
    // Load the enemy image from resources
    this.enemyImage =
        new Image(Objects.requireNonNull(getClass().getResourceAsStream("/enemy.png")));
  }

  /** Updates the enemy's position. */
  @Override
  public void update() {
    y += SPEED;
  }

  /**
   * Renders the enemy on the screen.
   *
   * @param gc The GraphicsContext to render the enemy.
   */
  @Override
  public void render(GraphicsContext gc) {
    if (enemyImage != null) {
      // Draw the enemy image, scaled to fit the enemy's dimensions
      gc.drawImage(enemyImage, x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
    } else {
      // Fallback: Render a red rectangle if the image is not loaded
      gc.setFill(Color.RED);
      gc.fillRect(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
    }
  }

  /**
   * Returns the width of the enemy.
   *
   * @return The width of the enemy.
   */
  @Override
  public double getWidth() {
    return WIDTH;
  }

  /**
   * Returns the height of the enemy.
   *
   * @return The height of the enemy.
   */
  @Override
  public double getHeight() {
    return HEIGHT;
  }

  /**
   * Sets the dead flag for the enemy.
   *
   * @param dead The dead flag to set.
   */
  public void setDead(boolean dead) {
    this.dead = dead;
  }

  /**
   * Returns whether the enemy is dead.
   *
   * @return True if the enemy is dead, false otherwise.
   */
  @Override
  public boolean isDead() {
    return dead;
  }
}
