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
