package com.example.spaceshootergamejavafx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.List;

public class Player extends GameObject {

    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private static final double SPEED = 5;
    private boolean moveLeft;
    private boolean moveRight;
    private boolean moveForward;
    private boolean moveBackward;
    private int health = 20;

    // In Player class
    @Override
    public double getWidth() {
        return WIDTH;
    }

    @Override
    public double getHeight() {
        return HEIGHT;
    }

    public Player(double x, double y) {
        super(x, y, 30, 30);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public void update() {
        if (moveLeft && x - SPEED > 0) {
            x -= SPEED;
        }

        if (moveRight && x + width + SPEED < SpaceShooter.WIDTH) {
            x += SPEED;
        }

        if (moveForward && y - SPEED > 0) {
            y -= SPEED;
        }

        if (moveBackward && y + height + SPEED < SpaceShooter.HEIGHT) {
            y += SPEED;
        }
    }


    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        gc.fillRect(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
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