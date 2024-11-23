package com.example.spaceshootergamejavafx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/** Represents a bullet fired by an enemy */
public class EnemyBullet extends GameObject {

  /** Width of the bullet */
  public static final int WIDTH = 4;

  /** Height of the bullet */
  public static final int HEIGHT = 20;

  /** Speed of the bullet */
  private static final double SPEED = 3;

  /** Flag to indicate if the bullet is dead */
  private boolean dead = false;

  /**
   * Creates a new enemy bullet at the specified position
   *
   * @param x The x-coordinate of the bullet
   * @param y The y-coordinate of the bullet
   */
  public EnemyBullet(double x, double y) {
    super(x, y, WIDTH, HEIGHT);
  }

  /** Updates the bullet's position */
  @Override
  public void update() {
    y += SPEED;
  }

  /**
   * Renders the bullet on the screen
   *
   * @param gc The GraphicsContext to render the bullet
   */
  @Override
  public void render(GraphicsContext gc) {
    gc.setFill(Color.RED);
    gc.fillRect(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
  }

  /**
   * Returns the width of the bullet
   *
   * @return The width of the bullet
   */
  @Override
  public double getWidth() {
    return WIDTH;
  }

  /**
   * Returns the height of the bullet
   *
   * @return The height of the bullet
   */
  @Override
  public double getHeight() {
    return HEIGHT;
  }

  /**
   * Sets the dead flag for the bullet
   *
   * @param dead The dead flag to set
   */
  public void setDead(boolean dead) {
    this.dead = dead;
  }

  /**
   * Returns whether the bullet is dead
   *
   * @return True if the bullet is dead, false otherwise
   */
  @Override
  public boolean isDead() {
    return dead;
  }
}
