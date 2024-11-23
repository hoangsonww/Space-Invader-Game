package com.example.spaceshootergamejavafx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.util.Objects;

public class Enemy extends GameObject {

  protected static final int WIDTH = 30;
  protected static final int HEIGHT = 30;
  public static double SPEED = 1;

  private final Image enemyImage; // Image for the enemy
  private boolean dead = false;

  public Enemy(double x, double y) {
    super(x, y, WIDTH, HEIGHT);
    // Load the enemy image from resources
    this.enemyImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/enemy.png")));
  }

  @Override
  public void update() {
    y += SPEED;
  }

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

  @Override
  public double getWidth() {
    return WIDTH;
  }

  @Override
  public double getHeight() {
    return HEIGHT;
  }

  public void setDead(boolean dead) {
    this.dead = dead;
  }

  @Override
  public boolean isDead() {
    return dead;
  }
}
