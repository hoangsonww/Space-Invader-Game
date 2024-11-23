package com.example.spaceshootergamejavafx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Objects;

/** Represents a power-up in the game */
public class PowerUp extends GameObject {

  /** Width of the power-up */
  public static final int WIDTH = 20; // Hitbox width remains unchanged

  /** Height of the power-up */
  public static final int HEIGHT = 20; // Hitbox height remains unchanged

  /** Speed of the power-up */
  private static final double SPEED = 2;

  /** Flag to indicate if the power-up is dead */
  private boolean isDead = false;

  /** Image for the power-up */
  private final Image powerUpImage; // Image for the power-up

  /**
   * Creates a new power-up at the specified position
   *
   * @param x The x-coordinate of the power-up
   * @param y The y-coordinate of the power-up
   */
  public PowerUp(double x, double y) {
    super(x, y, WIDTH, HEIGHT);
    // Load the power-up image from resources
    this.powerUpImage =
        new Image(Objects.requireNonNull(getClass().getResourceAsStream("/powerup.png")));
  }

  /** Updates the power-up's position */
  @Override
  public void update() {
    y += SPEED;
  }

  /**
   * Renders the power-up on the screen
   *
   * @param gc The GraphicsContext to render the power-up
   */
  @Override
  public void render(GraphicsContext gc) {
    if (powerUpImage != null) {
      // Double the visual size of the image
      double scaledWidth = WIDTH * 2;
      double scaledHeight = HEIGHT * 2;

      // Center the image around the hitbox's center
      double drawX = x - scaledWidth / 2;
      double drawY = y - scaledHeight / 2;

      gc.drawImage(powerUpImage, drawX, drawY, scaledWidth, scaledHeight);
    } else {
      // Fallback: Render a green rectangle to match the hitbox size
      gc.setFill(Color.GREEN);
      gc.fillRect(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
    }
  }

  /**
   * Returns the width of the power-up
   *
   * @return The width of the power-up
   */
  @Override
  public double getWidth() {
    return WIDTH;
  }

  /**
   * Returns the height of the power-up
   *
   * @return The height of the power-up
   */
  @Override
  public double getHeight() {
    return HEIGHT;
  }

  /**
   * Returns whether the power-up is dead
   *
   * @return Whether the power-up is dead
   */
  @Override
  public boolean isDead() {
    return this.isDead;
  }

  /**
   * Sets the dead flag for the power-up
   *
   * @param isDead The dead flag to set
   */
  public void setDead(boolean isDead) {
    this.isDead = isDead;
  }
}
