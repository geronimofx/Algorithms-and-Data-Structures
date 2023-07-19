package br.com.tadGrafo;

import java.util.Vector;

public class Vertice {
  private Object element;
  private Vector<Aresta> edges;

  Vertice(Object element) {
    this.element = element;
    this.edges = new Vector<Aresta>();
  }

  public Object getElement() {
    return this.element;
  }

  public void setElement(Object element) {
    this.element = element;
  }

  public void inserirAresta(Aresta edge) {
    edges.add(edge);
  };

  public void removeAresta(Aresta edge) {
    edges.remove(edge);
  };

  public Vector<Aresta> arestasIncidentes() {
    return edges;
  }
}