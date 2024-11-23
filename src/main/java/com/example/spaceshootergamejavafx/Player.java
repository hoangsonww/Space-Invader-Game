package com.example.spaceshootergamejavafx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Objects;

/** Represents the player object in the game */
public class Player extends GameObject {

  /** Width of the player hitbox */
  private static final int WIDTH = 40; // Updated to 2x the original size

  /** Height of the player hitbox */
  private static final int HEIGHT = 40; // Updated to 2x the original size

  /** Speed of the player */
  private static final double SPEED = 5;

  /** Flags to indicate move left direction */
  private boolean moveLeft;

  /** Flags to indicate move right direction */
  private boolean moveRight;

  /** Flags to indicate move forward direction */
  private boolean moveForward;

  /** Flags to indicate move backward direction */
  private boolean moveBackward;

  /** Health of the player */
  private int health = 20;

  /** Image for the player */
  private final Image overlayImage; // Overlay image for the player

  /** Stores the dead flag for the player */
  private boolean dead = false;

  /**
   * Creates a new player at the specified position
   *
   * @param x The x-coordinate of the player
   * @param y The y-coordinate of the player
   */
  public Player(double x, double y) {
    super(x, y, WIDTH, HEIGHT);
    // Load the overlay image from resources
    this.overlayImage =
        new Image(Objects.requireNonNull(getClass().getResourceAsStream("/player.png")));
  }

  /**
   * Returns the width of the player hitbox
   *
   * @return The width of the player hitbox
   */
  @Override
  public double getWidth() {
    return WIDTH;
  }

  /**
   * Returns the height of the player hitbox
   *
   * @return The height of the player hitbox
   */
  @Override
  public double getHeight() {
    return HEIGHT;
  }

  /**
   * Returns the health of the player
   *
   * @return The health of the player
   */
  public int getHealth() {
    return health;
  }

  /**
   * Sets the health of the player
   *
   * @param health The health of the player
   */
  public void setHealth(int health) {
    this.health = health;
  }

  /** Updates the player's position */
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

  /**
   * Renders the player on the screen
   *
   * @param gc The GraphicsContext to render the player
   */
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

  /**
   * Sets the move left flag
   *
   * @param moveLeft The move left flag to set
   */
  public void setMoveLeft(boolean moveLeft) {
    this.moveLeft = moveLeft;
  }

  /**
   * Sets the move right flag
   *
   * @param moveRight The move right flag to set
   */
  public void setMoveRight(boolean moveRight) {
    this.moveRight = moveRight;
  }

  /**
   * Sets the move forward flag
   *
   * @param moveForward The move forward flag to set
   */
  public void setMoveForward(boolean moveForward) {
    this.moveForward = moveForward;
  }

  /**
   * Sets the move backward flag
   *
   * @param moveBackward The move backward flag to set
   */
  public void setMoveBackward(boolean moveBackward) {
    this.moveBackward = moveBackward;
  }

  /**
   * Shoots a bullet from the player
   *
   * @param newObjects The list of new objects to add the bullet to
   */
  public void shoot(List<GameObject> newObjects) {
    Bullet bullet = new Bullet(x, y - HEIGHT / 2 - Bullet.HEIGHT);
    newObjects.add(bullet);
  }

  /**
   * Sets the dead flag for the player
   *
   * @param dead The dead flag to set
   */
  public void setDead(boolean dead) {
    this.dead = dead;
  }

  /**
   * Returns whether the player is dead
   *
   * @return true if the player is dead, false otherwise
   */
  @Override
  public boolean isDead() {
    return dead;
  }
}
