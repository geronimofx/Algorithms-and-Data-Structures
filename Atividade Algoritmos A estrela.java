// Atividade - Algoritmos A*
// Rafael Geronimo

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

package br.com.aEstrela;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AStar {
  private Maze maze;
  private List<Node> openList;
  private List<Node> closedList;

  public AStar(Maze maze) {
    this.maze = maze;
    this.openList = new ArrayList<>();
    this.closedList = new ArrayList<>();
  }

  public List<Node> findPath() {
    Node startNode = new Node(maze.getStartX(), maze.getStartY());
    Node targetNode = new Node(maze.getTargetX(), maze.getTargetY());

    openList.add(startNode);

    while (!openList.isEmpty()) {
      Node currentNode = getLowestFNode();

      if (currentNode.getX() == targetNode.getX() && currentNode.getY() == targetNode.getY()) {
        return constructPath(currentNode);
      }

      openList.remove(currentNode);
      closedList.add(currentNode);

      List<Node> neighbors = getNeighbors(currentNode);

      for (Node neighbor : neighbors) {
        if (closedList.contains(neighbor)) {
          continue;
        }

        int newG = currentNode.getG() + 1;

        if (newG < neighbor.getG() || !openList.contains(neighbor)) {
          neighbor.setG(newG);
          neighbor.setH(calculateH(neighbor, targetNode));
          neighbor.setParent(currentNode);

          if (!openList.contains(neighbor)) {
            openList.add(neighbor);
          }
        }
      }
    }

    return Collections.emptyList();
  }

  private Node getLowestFNode() {
    Node lowestFNode = openList.get(0);

    for (int i = 1; i < openList.size(); i++) {
      Node node = openList.get(i);

      if (node.getF() < lowestFNode.getF()) {
        lowestFNode = node;
      }
    }

    return lowestFNode;
  }

  private List<Node> getNeighbors(Node node) {
    int x = node.getX();
    int y = node.getY();
    int[][] grid = maze.getGrid();
    List<Node> neighbors = new ArrayList<>();

    if (x > 0 && grid[x - 1][y] != 1) {
      neighbors.add(new Node(x - 1, y));
    }

    if (x < maze.getSizeX() - 1 && grid[x + 1][y] != 1) {
      neighbors.add(new Node(x + 1, y));
    }

    if (y > 0 && grid[x][y - 1] != 1) {
      neighbors.add(new Node(x, y - 1));
    }

    if (y < maze.getSizeY() - 1 && grid[x][y + 1] != 1) {
      neighbors.add(new Node(x, y + 1));
    }

    return neighbors;
  }

  private int calculateH(Node node, Node targetNode) {
    int dx = Math.abs(node.getX() - targetNode.getX());
    int dy = Math.abs(node.getY() - targetNode.getY());
    return dx + dy;
  }

  private List<Node> constructPath(Node node) {
    List<Node> path = new ArrayList<>();

    while (node != null) {
      path.add(node);
      node = node.getParent();
    }

    Collections.reverse(path);
    return path;
  }
}

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

package br.com.aEstrela;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Maze {
  private int[][] grid;
  private int startX;
  private int startY;
  private int targetX;
  private int targetY;

  public Maze(String filePath) throws IOException {
    loadMazeFromFile(filePath);
  }

  private void loadMazeFromFile(String filePath) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(filePath));
    String line;
    int rowCount = 0;
    int colCount = 0;
    List<String> lines = new ArrayList<>();

    while ((line = reader.readLine()) != null) {
      lines.add(line);
      rowCount++;
      colCount = Math.max(colCount, line.length());
    }

    grid = new int[rowCount][colCount];

    for (int i = 0; i < rowCount; i++) {
      String currentLine = lines.get(i);
      for (int j = 0; j < currentLine.length(); j++) {
        char c = currentLine.charAt(j);
        int cellValue = Character.getNumericValue(c);

        if (cellValue == 2) {
          startX = i;
          startY = j;
        } else if (cellValue == 3) {
          targetX = i;
          targetY = j;
        }

        grid[i][j] = cellValue;
      }
    }

    reader.close();
  }

  public int[][] getGrid() {
    return grid;
  }

  public int getStartX() {
    return startX;
  }

  public int getStartY() {
    return startY;
  }

  public int getTargetX() {
    return targetX;
  }

  public int getTargetY() {
    return targetY;
  }

  public int getSizeX() {
    return grid.length;
  }

  public int getSizeY() {
    return grid[0].length;
  }
}
