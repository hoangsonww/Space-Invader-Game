package com.example.spaceshootergamejavafx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.util.List;
import java.util.Objects;

public class BossEnemy extends Enemy {

  private int health = 5;
  private static final int WIDTH = 50; // Hitbox width
  private static final int HEIGHT = 50; // Hitbox height
  private int numHits = 5;

  private final Image bossImage; // Image for the boss
  private double horizontalSpeed = 1.5; // Horizontal movement speed

  public BossEnemy(double x, double y) {
    super(x, y);
    SPEED = 1.0; // Vertical speed
    health = 5;

    // Load the boss image from resources
    this.bossImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/boss.png")));
  }

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

  public void takeDamage() {
    health--;
    if (health <= 0) {
      setDead(true);
    }
  }

  public void shoot(List<GameObject> newObjects) {
    if (Math.random() < 0.015) {
      newObjects.add(new EnemyBullet(x, y + HEIGHT / 2));
    }
  }

  public boolean isDead() {
    return health <= 0;
  }

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

  public void hit() {
    health--;
    if (health <= 0) {
      setDead(true);
    }
  }
}
