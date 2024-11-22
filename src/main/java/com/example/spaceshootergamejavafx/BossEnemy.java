package com.example.spaceshootergamejavafx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class BossEnemy extends Enemy {

  private int health = 5;

  private int WIDTH;

  private int HEIGHT;

  private int numHits = 5;

  public BossEnemy(double x, double y) {
    super(x, y);
    SPEED = 1.0;
    WIDTH = 50;
    HEIGHT = 50;
    health = 5;
  }

  @Override
  public void update() {
    // BossEnemy stays at the top of the screen
    if (y < 40) {
      y += SPEED;
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

  public void decreaseHealth() {
    health--;
  }

  public boolean isDead() {
    return health <= 0;
  }

  @Override
  public void render(GraphicsContext gc) {
    gc.setFill(Color.BLUE);
    gc.fillRect(x - WIDTH / 2, y - HEIGHT / 2, WIDTH * 2, HEIGHT * 2);
  }

  public void hit() {
    health--;
    if (health <= 0) {
      setDead(true);
    }
  }
}
