package br.com.prim;

public class PrimAlgorithmExample {
  public static void main(String[] args) {
    int vertices = 5;
    Graph graph = new Graph(vertices);

    graph.addEdge(0, 1, 2);
    graph.addEdge(0, 3, 6);
    graph.addEdge(1, 2, 3);
    graph.addEdge(1, 3, 8);
    graph.addEdge(1, 4, 5);
    graph.addEdge(2, 4, 7);
    graph.addEdge(3, 4, 9);

    graph.primMST();
  }
}
