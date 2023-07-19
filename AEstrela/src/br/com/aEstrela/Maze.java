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
