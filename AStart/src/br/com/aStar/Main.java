package br.com.aStar;

import java.io.IOException;

public class Main {
  public static void main(String[] args) {
    try {
      new AStar("AStart/src/br/com/aStar/labirinto.txt");
    } catch (IOException err) {
      System.out.println(err);
    }
  }
}
