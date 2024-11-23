package com.example.spaceshootergamejavafx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.util.Objects;

public class PowerUp extends GameObject {

  public static final int WIDTH = 20; // Hitbox width remains unchanged
  public static final int HEIGHT = 20; // Hitbox height remains unchanged

  private static final double SPEED = 2;
  private boolean isDead = false;

  private final Image powerUpImage; // Image for the power-up

  public PowerUp(double x, double y) {
    super(x, y, WIDTH, HEIGHT);
    // Load the power-up image from resources
    this.powerUpImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/powerup.png")));
  }

  @Override
  public void update() {
    y += SPEED;
  }

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

  @Override
  public double getWidth() {
    return WIDTH;
  }

  @Override
  public double getHeight() {
    return HEIGHT;
  }

  @Override
  public boolean isDead() {
    return this.isDead;
  }

  public void setDead(boolean isDead) {
    this.isDead = isDead;
  }
}
