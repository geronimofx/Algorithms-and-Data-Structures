package br.com.aEstrela;

import java.util.List;

public class Main {
  public static void main(String[] args) {
    try {
      Maze maze = new Maze("AEstrela/src/br/com/aEstrela/labirinto.txt");
      AStar aStar = new AStar(maze);

      long startTime = System.nanoTime();
      List<Node> path = aStar.findPath();
      long endTime = System.nanoTime();
      long duration = (endTime - startTime) / 1000000; // Convert to milliseconds

      if (!path.isEmpty()) {
        System.out.println("Path found:");
        printPath(maze, path);
        System.out.println("Time taken: " + duration + " ms");
      } else {
        System.out.println("No path found.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void printPath(Maze maze, List<Node> path) {
    int[][] grid = maze.getGrid();

    for (int i = 0; i < maze.getSizeX(); i++) {
      for (int j = 0; j < maze.getSizeY(); j++) {
        Node node = getNodeAtPosition(path, i, j);

        if (node != null) {
          System.out.print("P ");
        } else {
          System.out.print(grid[i][j] + " ");
        }
      }

      System.out.println();
    }
  }

  private static Node getNodeAtPosition(List<Node> nodes, int x, int y) {
    for (Node node : nodes) {
      if (node.getX() == x && node.getY() == y) {
        return node;
      }
    }

    return null;
  }
}
