package br.com.prim;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

class Graph {
  private int vertices;
  private List<List<Edge>> adjacencyList;

  public Graph(int vertices) {
    this.vertices = vertices;
    adjacencyList = new ArrayList<>(vertices);
    for (int i = 0; i < vertices; i++) {
      adjacencyList.add(new ArrayList<>());
    }
  }

  public void addEdge(int source, int destination, int weight) {
    Edge edge = new Edge(source, destination, weight);
    adjacencyList.get(source).add(edge);

    edge = new Edge(destination, source, weight);
    adjacencyList.get(destination).add(edge);
  }

  public void primMST() {
    boolean[] visited = new boolean[vertices];
    int[] parent = new int[vertices];
    int[] key = new int[vertices];

    Arrays.fill(key, Integer.MAX_VALUE);
    Arrays.fill(visited, false);

    PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(vertices, new EdgeComparator());
    key[0] = 0;
    parent[0] = -1;
    priorityQueue.offer(new Edge(0, 0, 0));

    while (!priorityQueue.isEmpty()) {
      int u = priorityQueue.poll().destination;
      visited[u] = true;

      for (Edge edge : adjacencyList.get(u)) {
        int v = edge.destination;
        int weight = edge.weight;
        if (!visited[v] && weight < key[v]) {
          priorityQueue.remove(new Edge(v, 0, key[v]));
          key[v] = weight;
          priorityQueue.offer(new Edge(v, v, weight));
          parent[v] = u;
        }
      }
    }

    System.out.println("Minimum Spanning Tree:");
    for (int i = 1; i < vertices; i++) {
      System.out.println("Edge: " + parent[i] + " - " + i);
    }
  }

  private static class Edge {
    int source;
    int destination;
    int weight;

    Edge(int source, int destination, int weight) {
      this.source = source;
      this.destination = destination;
      this.weight = weight;
    }
  }

  private static class EdgeComparator implements java.util.Comparator<Edge> {
    @Override
    public int compare(Edge edge1, Edge edge2) {
      return Integer.compare(edge1.weight, edge2.weight);
    }
  }
}