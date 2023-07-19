package br.com.tadGrafo;

public class Main {
  public static void main(String[] args) {
    Grafo grafo = new Grafo();

    // Adicionar vértices
    Vertice v1 = grafo.inserirVertice("Luffy");
    Vertice v2 = grafo.inserirVertice("Ichigo");
    Vertice v3 = grafo.inserirVertice("Asta");
    Vertice v4 = grafo.inserirVertice("Mashle");

    // Adicionar arestas
    Aresta e1 = grafo.inserirAresta(v1, v2, "Edge 1", false);
    Aresta e2 = grafo.inserirAresta(v1, v3, "Edge 2", true);
    Aresta e3 = grafo.inserirAresta(v2, v4, "Edge 3", true);

    // Exibir o grafo formado
    System.out.println("Vértices:");
    for (Vertice v : grafo.vertices()) {
      System.out.println(v.getElement());
    }

    System.out.println("===========================");

    System.out.println("Arestas:");
    for (Aresta e : grafo.arestas()) {
      System.out.println(e.getElement() + " (Direcionado: " + grafo.isDirecionado(e) + ")");
    }
    System.out.println("===========================");

    // exibir Grafo
    grafo.imprimirGrafo();

    System.out.println("===========================");

    // Remoção de vértice
    Object elementoRemovido = grafo.removeVertice(v3);
    System.out.println("Elemento removido: " + elementoRemovido);

    // Remoção de aresta
    Object elementoRemovidoAresta = grafo.removeAresta(e3);
    System.out.println("Elemento removido da aresta: " + elementoRemovidoAresta);

    System.out.println("===========================");

    // Exibir o grafo formado
    System.out.println("Vértices:");
    for (Vertice v : grafo.vertices()) {
      System.out.println(v.getElement());
    }

    System.out.println("===========================");

    System.out.println("Arestas:");
    for (Aresta e : grafo.arestas()) {
      System.out.println(e.getElement() + " (Direcionado: " + grafo.isDirecionado(e) + ")");
    }

    // exibir Grafo
    grafo.imprimirGrafo();
  }

}
