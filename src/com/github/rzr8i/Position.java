package com.github.rzr8i;

public class Position {
  public int x, y;

  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  // Adds two positions and returns a new position.
  public Position add(Position other) {
    return new Position(this.x + other.x, this.y + other.y);
  }
  
  public boolean equals(Position other) {
    return this.x == other.x && this.y == other.y;
  }
}
