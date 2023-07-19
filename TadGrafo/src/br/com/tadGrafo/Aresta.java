package br.com.tadGrafo;

public class Aresta {
  private Vertice fromVertex;
  private Vertice toVertex;
  private Object element;
  private boolean isDirected;

  Aresta(Vertice fromVertex, Vertice toVertex, Object element, boolean isDirected) {
    this.fromVertex = fromVertex;
    this.toVertex = toVertex;
    this.element = element;
    this.isDirected = isDirected;
  }

  public Vertice getFromVertex() {
    return this.fromVertex;
  }

  public Vertice getToVertex() {
    return this.toVertex;
  }

  public Object getElement() {
    return this.element;
  }

  public void setElement(Object element) {
    this.element = element;
  }

  public boolean getIsDirected() {
    return this.isDirected;
  }
}
