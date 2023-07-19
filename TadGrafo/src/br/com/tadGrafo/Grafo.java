package br.com.tadGrafo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class Grafo {
  private Vector<Vertice> vertices;
  private Vector<Aresta> edges;

  public Grafo() {
    vertices = new Vector<Vertice>();
    edges = new Vector<Aresta>();
  }

  public Vector<Vertice> finalVertices(Aresta edge) {
    Vector<Vertice> vertexList = new Vector<Vertice>();
    vertexList.add(edge.getFromVertex());
    vertexList.add(edge.getToVertex());
    return vertexList;
  }

  public Vertice oposto(Vertice vertex, Aresta edge) {
    if (edge.getFromVertex() == vertex || edge.getToVertex() == vertex) {
      return edge.getFromVertex() == vertex ? edge.getToVertex() : edge.getFromVertex();
    }
    return vertex;
  }

  public boolean isAdjacente(Vertice vertex1, Vertice vertex2) {
    boolean isAdjacente = false;
    for (Aresta edge : vertex1.arestasIncidentes()) {
      if (edge.getFromVertex() == vertex2 || edge.getToVertex() == vertex2) {
        isAdjacente = true;
        break;
      }
    }
    return isAdjacente;
  }

  public void substituir(Vertice vertex, Object value) {
    vertex.setElement(value);
  }

  public void substituir(Aresta edge, Object value) {
    edge.setElement(value);
  }

  public Vertice inserirVertice(Object element) {
    Vertice vertex = new Vertice(element);
    vertices.add(vertex);
    return vertex;
  }

  public Aresta inserirAresta(Vertice fromVertex, Vertice toVertex, Object element, boolean isDirected) {
    Aresta edge = new Aresta(fromVertex, toVertex, element, isDirected);
    edges.add(edge);
    fromVertex.inserirAresta(edge);
    toVertex.inserirAresta(edge);
    return edge;
  }

  public Object removeVertice(Vertice vertex) {
    Object removedElement = vertex.getElement();
    while (vertex.arestasIncidentes().size() > 0) {
      removeAresta(vertex.arestasIncidentes().get(0));
    }
    vertices.remove(vertex);
    return removedElement;
  }

  public Object removeAresta(Aresta edge) {
    Object removedElement = edge.getElement();
    edge.getFromVertex().removeAresta(edge);
    edge.getToVertex().removeAresta(edge);
    edges.remove(edge);
    return removedElement;
  }

  public Iterator<Aresta> arestasIncidentes(Vertice vertex) {
    return vertex.arestasIncidentes().iterator();
  }

  public boolean isDirecionado(Aresta edge) {
    return edge.getIsDirected();
  }

  public List<Vertice> vertices() {
    return new ArrayList<>(vertices);
  }

  public List<Aresta> arestas() {
    return new ArrayList<>(edges);
  }

  public void imprimirGrafo() {
    // Percorre todos os vértices no grafo.
    for (Vertice v : vertices) {
      // Imprime o elemento do vértice atual e a string " está conectado com: ".
      System.out.print(v.getElement() + " está conectado com: ");
      // Percorre todas as arestas incidentes do vértice atual.
      for (Aresta a : v.arestasIncidentes()) {
        // Obtém o vértice oposto à aresta atual.
        Vertice oposto = oposto(v, a);
        // Imprime o elemento do vértice oposto, seguido de uma vírgula e um espaço.
        System.out.print(oposto.getElement() + ", ");
      }
      // Imprime uma quebra de linha para separar as descrições das conexões de
      // diferentes vértices.
      System.out.println();
    }
  }

}