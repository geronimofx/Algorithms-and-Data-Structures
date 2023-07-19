package br.com.aEstrela;

public class Node {
  private int x;
  private int y;
  private int g;
  private int h;
  private Node parent;

  public Node(int x, int y) {
    this.x = x;
    this.y = y;
    this.g = 0;
    this.h = 0;
    this.parent = null;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getG() {
    return g;
  }

  public void setG(int g) {
    this.g = g;
  }

  public int getH() {
    return h;
  }

  public void setH(int h) {
    this.h = h;
  }

  public int getF() {
    return g + h;
  }

  public Node getParent() {
    return parent;
  }

  public void setParent(Node parent) {
    this.parent = parent;
  }
}
