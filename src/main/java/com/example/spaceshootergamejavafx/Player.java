package com.example.spaceshootergamejavafx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.util.List;
import java.util.Objects;

public class Player extends GameObject {

  private static final int WIDTH = 40; // Updated to 2x the original size
  private static final int HEIGHT = 40; // Updated to 2x the original size
  private static final double SPEED = 5;
  private boolean moveLeft;
  private boolean moveRight;
  private boolean moveForward;
  private boolean moveBackward;
  private int health = 20;

  private final Image overlayImage; // Overlay image for the player

  public Player(double x, double y) {
    super(x, y, WIDTH, HEIGHT);
    // Load the overlay image from resources
    this.overlayImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/player.png")));
  }

  @Override
  public double getWidth() {
    return WIDTH;
  }

  @Override
  public double getHeight() {
    return HEIGHT;
  }

  public int getHealth() {
    return health;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  @Override
  public void update() {
    // Move left
    if (moveLeft && x - WIDTH / 2 - SPEED >= 0) {
      x -= SPEED;
    }

    // Move right
    if (moveRight && x + WIDTH / 2 + SPEED <= SpaceShooter.WIDTH) {
      x += SPEED;
    }

    // Move up
    if (moveForward && y - HEIGHT / 2 - SPEED >= 0) {
      y -= SPEED;
    }

    // Move down
    if (moveBackward && y + HEIGHT / 2 + SPEED <= SpaceShooter.HEIGHT) {
      y += SPEED;
    }
  }

  @Override
  public void render(GraphicsContext gc) {
    // Check if the overlay image exists
    if (overlayImage != null) {
      double imageWidth = overlayImage.getWidth();
      double imageHeight = overlayImage.getHeight();

      // Calculate aspect ratio
      double aspectRatio = imageWidth / imageHeight;

      // Double the size of the visual image dimensions
      double scaledWidth = WIDTH * 2; // Double the width
      double scaledHeight = HEIGHT * 2; // Double the height

      // Maintain aspect ratio
      if (scaledHeight > scaledWidth / aspectRatio) {
        scaledHeight = scaledWidth / aspectRatio;
      } else {
        scaledWidth = scaledHeight * aspectRatio;
      }

      // Center the image within the hitbox
      double drawX = x - scaledWidth / 2;
      double drawY = y - scaledHeight / 2;

      gc.drawImage(overlayImage, drawX, drawY, scaledWidth, scaledHeight);
    } else {
      // Fallback: Render a rectangle if the image is not loaded
      gc.setFill(Color.BLUE);
      gc.fillRect(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
    }
  }

  public void setMoveLeft(boolean moveLeft) {
    this.moveLeft = moveLeft;
  }

  public void setMoveRight(boolean moveRight) {
    this.moveRight = moveRight;
  }

  public void setMoveForward(boolean moveForward) {
    this.moveForward = moveForward;
  }

  public void setMoveBackward(boolean moveBackward) {
    this.moveBackward = moveBackward;
  }

  public void shoot(List<GameObject> newObjects) {
    Bullet bullet = new Bullet(x, y - HEIGHT / 2 - Bullet.HEIGHT);
    newObjects.add(bullet);
  }

  private boolean dead = false;

  public void setDead(boolean dead) {
    this.dead = dead;
  }

  @Override
  public boolean isDead() {
    return dead;
  }
}
