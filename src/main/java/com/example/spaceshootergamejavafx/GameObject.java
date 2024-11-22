package com.example.spaceshootergamejavafx;

import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;

public abstract class GameObject {

  protected double x;
  protected double y;
  protected double width;
  protected double height;

  public GameObject(double x, double y, double width, double height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  public abstract void update();

  public abstract void render(GraphicsContext gc);

  public abstract boolean isDead();

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public Bounds getBounds() {
    return new Rectangle(x - getWidth() / 2, y - getHeight() / 2, getWidth(), getHeight())
        .getBoundsInLocal();
  }

  public abstract double getWidth();

  public abstract double getHeight();
}
