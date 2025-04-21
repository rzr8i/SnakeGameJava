package com.github.rzr8i;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Snake {
  private static final int GRID_WIDTH = 40;
  private static final int GRID_HEIGHT = 10;

  private static final int EMPTY_CELL = 0;
  private static final int HEAD_CELL = 1;
  private static final int BODY_CELL = 2;
  private static final int FRUIT_CELL = 3;
  private int grid[][] = new int[GRID_HEIGHT][GRID_WIDTH];

  // frist item of this list is always the snake's head.
  private ArrayList<Position> snake = new ArrayList<>();

  private Position fruitPos;

  private static final Position D_UP = new Position(0, -1);
  private static final Position D_DOWN = new Position(0, 1);
  private static final Position D_RIGHT = new Position(1, 0);
  private static final Position D_LEFT = new Position(-1, 0);
  private Position direction = D_RIGHT;

  private Scanner sc;

  public Snake() {
    int middleX = GRID_WIDTH / 2;
    int middleY = GRID_HEIGHT / 2;

    // Create a snake with initial length of 4.
    snake.add(new Position(middleX, middleY));
    snake.add(new Position(middleX - 1, middleY));
    snake.add(new Position(middleX - 2, middleY));
    snake.add(new Position(middleX - 3, middleY));

    createRandomFruit();

    refreshGrid();
  }

  public void start() {
    while (true) {
      clearScreen();
      draw();
      readDirection();
      move();
      refreshGrid();
    }
  }

  private void readDirection() {
    if (sc == null)
      sc = new Scanner(System.in);

    System.out.print("Enter direction (WASD): ");
    String input;
    input = sc.next().toLowerCase();

    switch (input) {
      case "w":
        if (direction == D_DOWN)
          return;
        direction = D_UP;
        break;
      case "a":
        if (direction == D_RIGHT)
          return;
        direction = D_LEFT;
        break;
      case "s":
        if (direction == D_UP)
          return;
        direction = D_DOWN;
        break;
      case "d":
        if (direction == D_LEFT)
          return;
        direction = D_RIGHT;
        break;

      default:
        // ignore
        break;
    }
  }

  private void move() {
    Position newhead = snake.get(0).add(direction);
    snake.addFirst(newhead);

    // Collision with wall
    if (newhead.x < 0 || newhead.x >= GRID_WIDTH || newhead.y < 0 || newhead.y >= GRID_HEIGHT) {
      System.out.println("GAME OVER");
      System.exit(0);
    }
    // Collision with body
    for (Position p : snake.subList(1, snake.size())) {
      if (p.equals(newhead)) {
        System.out.println("GAME OVER");
        System.exit(0);
      }
    }

    if (fruitPos.equals(newhead))
      createRandomFruit();
    else
      snake.removeLast();
  }

  private void refreshGrid() {
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        grid[i][j] = EMPTY_CELL;
      }
    }

    for (Position p : snake.subList(1, snake.size()))
      grid[p.y][p.x] = BODY_CELL;

    grid[snake.get(0).y][snake.get(0).x] = HEAD_CELL;

    grid[fruitPos.y][fruitPos.x] = FRUIT_CELL;
  }

  private void createRandomFruit() {
    Random random = new Random();
    boolean isOK = true;
    int x, y;
    do {
      isOK = true;
      x = random.nextInt(GRID_WIDTH);
      y = random.nextInt(GRID_HEIGHT);
      Position fruitPos = new Position(x, y);

      for (Position p : snake) {
        if (p.equals(fruitPos))
          isOK = false;
      }
    } while (!isOK);

    fruitPos = new Position(x, y);
  }

  private void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  private void draw() {
    for (int i = 0; i < GRID_WIDTH + 2; i++)
      System.out.print('|');
    System.out.println();

    for (int i = 0; i < grid.length; i++) {
      System.out.print('|');
      for (int j = 0; j < grid[i].length; j++) {
        switch (grid[i][j]) {
          case EMPTY_CELL:
            System.out.print(' ');
            break;
          case HEAD_CELL:
            System.out.print('@');
            break;
          case BODY_CELL:
            System.out.print('*');
            break;
          case FRUIT_CELL:
            System.out.print('#');
            break;
        }
      }
      System.out.println('|');
    }

    for (int i = 0; i < GRID_WIDTH + 2; i++)
      System.out.print('|');
    System.out.println();
  }
}
