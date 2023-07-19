package br.com.aStar;

import br.com.tadGrafo.Vertice;

public class VertexValue {
  private int x;
  private int y;
  private double gScore = Double.MAX_VALUE;
  private double hScore = Double.MAX_VALUE;
  private double fScore = Double.MAX_VALUE;
  private Vertice cameFrom = null;

  public VertexValue(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  public double getGScore() {
    return this.gScore;
  }

  public double getHScore() {
    return this.hScore;
  }

  public double getFScore() {
    return this.fScore;
  }

  public void setGScore(double newGScore) {
    this.gScore = newGScore;
    this.fScore = this.gScore + this.hScore;
  }

  public void setHScore(Vertice goal) {
    VertexValue goalVertexValue = (VertexValue) goal.getElement();
    this.hScore = Math
        .sqrt(Math.pow(this.x - goalVertexValue.getX(), 2) + Math.pow(this.y - goalVertexValue.getY(), 2));
    this.fScore = this.gScore + this.hScore;
  }

  public Vertice getCameFrom() {
    return this.cameFrom;
  }

  public void setCameFrom(Vertice newCameFrom) {
    this.cameFrom = newCameFrom;
  }

  public void reset() {
    this.gScore = Double.MAX_VALUE;
    this.hScore = Double.MAX_VALUE;
    this.fScore = Double.MAX_VALUE;
    this.cameFrom = null;
  }
}