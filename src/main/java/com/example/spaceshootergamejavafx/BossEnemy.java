package com.example.spaceshootergamejavafx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Objects;

/** BossEnemy class represents a boss enemy in the game. */
public class BossEnemy extends Enemy {

  /** Health of the boss enemy. */
  private int health = 5;

  /** Width of the boss enemy hitbox. */
  private static final int WIDTH = 50;

  /** Height of the boss enemy hitbox. */
  private static final int HEIGHT = 50; // Hitbox height

  /** Number of hits the boss can take before dying. */
  private int numHits = 5;

  /** Image for the boss enemy. */
  private final Image bossImage;

  /** Horizontal movement speed of the boss enemy. */
  private double horizontalSpeed = 1.5;

  /**
   * Creates a new BossEnemy object at the specified position.
   *
   * @param x X-coordinate of the boss enemy.
   * @param y Y-coordinate of the boss enemy.
   */
  public BossEnemy(double x, double y) {
    super(x, y);
    SPEED = 1.0; // Vertical speed
    health = 5;

    // Load the boss image from resources
    this.bossImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/boss.png")));
  }

  /** Updates the boss enemy's position and behavior. */
  @Override
  public void update() {
    // BossEnemy moves vertically down
    if (y < 40) {
      y += SPEED;
    }

    // BossEnemy also moves horizontally back and forth
    x += horizontalSpeed;

    // Reverse direction if the boss reaches the edges of the screen
    if (x - WIDTH / 2 <= 0 || x + WIDTH / 2 >= SpaceShooter.WIDTH) {
      horizontalSpeed = -horizontalSpeed;
    }
  }

  /** Damages the boss enemy by reducing its health. */
  public void takeDamage() {
    health--;
    if (health <= 0) {
      setDead(true);
    }
  }

  /**
   * Shoots bullets at the player.
   *
   * @param newObjects List of GameObjects to add new objects to.
   */
  public void shoot(List<GameObject> newObjects) {
    if (Math.random() < 0.015) {
      newObjects.add(new EnemyBullet(x, y + HEIGHT / 2));
    }
  }

  /** Checks if the boss enemy is dead. */
  public boolean isDead() {
    return health <= 0;
  }

  /**
   * Renders the boss enemy on the screen.
   *
   * @param gc GraphicsContext object to render the boss enemy.
   */
  @Override
  public void render(GraphicsContext gc) {
    if (bossImage != null) {
      // Double the visual size of the image
      double scaledWidth = WIDTH * 2;
      double scaledHeight = HEIGHT * 2;

      // Center the image around the hitbox's center
      double drawX = x - scaledWidth / 2;
      double drawY = y - scaledHeight / 2;

      gc.drawImage(bossImage, drawX, drawY, scaledWidth, scaledHeight);
    } else {
      // Fallback: Render a blue rectangle to match the hitbox size
      gc.setFill(Color.BLUE);
      gc.fillRect(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
    }
  }

  /** Damages the boss enemy by reducing its health. */
  public void hit() {
    health--;
    if (health <= 0) {
      setDead(true);
    }
  }
}
