package com.example.spaceshootergamejavafx;

import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;

/** Represents a game object in the game */
public abstract class GameObject {

  /** X-coordinate of the game object */
  protected double x;

  /** Y-coordinate of the game object */
  protected double y;

  /** Width of the game object */
  protected double width;

  /** Height of the game object */
  protected double height;

  /**
   * Creates a new game object at the specified position with the given dimensions.
   *
   * @param x The x-coordinate of the game object
   * @param y The y-coordinate of the game object
   * @param width The width of the game object
   * @param height The height of the game object
   */
  public GameObject(double x, double y, double width, double height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  /** Updates the game object's position and behavior. */
  public abstract void update();

  /**
   * Renders the game object on the screen.
   *
   * @param gc The GraphicsContext to render the game object
   */
  public abstract void render(GraphicsContext gc);

  /**
   * Checks if the game object is dead.
   *
   * @return true if the game object is dead, false otherwise
   */
  public abstract boolean isDead();

  /**
   * Returns the x-coordinate of the game object.
   *
   * @return The x-coordinate of the game object
   */
  public double getX() {
    return x;
  }

  /**
   * Returns the y-coordinate of the game object.
   *
   * @return The y-coordinate of the game object
   */
  public double getY() {
    return y;
  }

  /**
   * Returns the bounds of the game object.
   *
   * @return The bounds of the game object
   */
  public Bounds getBounds() {
    return new Rectangle(x - getWidth() / 2, y - getHeight() / 2, getWidth(), getHeight())
        .getBoundsInLocal();
  }

  /**
   * Returns the width of the game object.
   *
   * @return The width of the game object
   */
  public abstract double getWidth();

  /**
   * Returns the height of the game object.
   *
   * @return The height of the game object
   */
  public abstract double getHeight();
}
