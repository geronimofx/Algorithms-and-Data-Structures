package br.com.aStar;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.Scanner;

import br.com.tadGrafo.Grafo;
import br.com.tadGrafo.Vertice;
import br.com.tadGrafo.Aresta;

public class AStar {
  private Grafo graph;

  @SuppressWarnings("unchecked")
  public AStar(String filename) throws IOException {
    this.graph = new Grafo();
    Scanner file = new Scanner(new FileReader(filename));
    Vector<Vector<Vertice>> graphList = new Vector<Vector<Vertice>>();
    ;
    int x = 0;
    Vector<Vertice> starts = new Vector<Vertice>();
    Vector<Vertice> goals = new Vector<Vertice>();
    while (file.hasNextLine()) {
      String[] line = file.nextLine().trim().split("");
      Vector<Vertice> auxList = new Vector<Vertice>();
      for (int y = 0; y < line.length; y++) {
        if (line[y].equals("1")) {
          auxList.add(null);
        } else {
          VertexValue newVertexValue = new VertexValue(x, y);
          Vertice newVertex = this.graph.inserirVertice(newVertexValue);
          // Setar arestas para cima
          if (x > 0) {
            Vertice upVertex = graphList.get(x - 1).get(y);
            if (upVertex != null) {
              this.graph.inserirAresta(upVertex, newVertex, "caminho", false);
            }
            if (y > 0) {
              Vertice upLeftVertex = graphList.get(x - 1).get(y - 1);
              if (upLeftVertex != null) {
                this.graph.inserirAresta(upLeftVertex, newVertex, "caminho", false);
              }
            }
            if (y + 1 < line.length) {
              Vertice upRightVertex = graphList.get(x - 1).get(y + 1);
              if (upRightVertex != null) {
                this.graph.inserirAresta(upRightVertex, newVertex, "caminho", false);
              }
            }
          }
          if (y > 0) {
            Vertice leftVertex = auxList.get(y - 1);
            if (leftVertex != null) {
              this.graph.inserirAresta(leftVertex, newVertex, "caminho", false);
            }
          }
          auxList.add(newVertex);
          if (line[y].equals("2")) {
            starts.add(newVertex);
          }
          if (line[y].equals("3")) {
            goals.add(newVertex);
          }
        }
      }
      graphList.add(auxList);
      x++;
    }
    HashMap<String, Object> finalResult = new HashMap<String, Object>();
    for (Vertice s : starts) {
      for (Vertice g : goals) {
        List<Vertice> vertices = this.graph.vertices();
        for (Vertice vertex : vertices) {
          VertexValue vertexValue = (VertexValue) vertex.getElement();
          vertexValue.reset();
        }
        HashMap<String, Object> result = find(s, g);
        if (result != null) {
          if (finalResult.isEmpty() || (double) finalResult.get("fScore") > (double) result.get("fScore")) {
            finalResult = result;
          }
        }
      }
    }

    Vector<Vertice> finalPath = new Vector<Vertice>();
    if (finalResult.isEmpty() == false) {
      finalPath = (Vector<Vertice>) finalResult.get("finalPath");
    }
    print(graphList, finalPath, starts, goals);
  }

  private Vertice getLowestCost(Vector<Vertice> openList) {
    Vertice lowerCostVertex = openList.get(0);
    for (int x = 1; x < openList.size(); x++) {
      Vertice auxVertex = openList.get(x);
      VertexValue auxVertexValue = (VertexValue) auxVertex.getElement();
      VertexValue lowerCostVertexValue = (VertexValue) lowerCostVertex.getElement();
      if (auxVertexValue.getFScore() < lowerCostVertexValue.getFScore()) {
        lowerCostVertex = auxVertex;
      }
    }
    return lowerCostVertex;
  }

  private double calculateWeight(Vertice fromVertex, Vertice toVertex) {
    VertexValue fromVertexValue = (VertexValue) fromVertex.getElement();
    VertexValue toVertexValue = (VertexValue) toVertex.getElement();
    if (fromVertexValue.getX() == toVertexValue.getX() || fromVertexValue.getY() == toVertexValue.getY()) {
      return 10;
    }
    return 14;
  }

  private Vector<Vertice> reconstructPath(Vertice start, Vertice current) {
    Vector<Vertice> finalPath = new Vector<Vertice>();
    while (start != current) {
      finalPath.add(current);
      VertexValue currentVertexValue = (VertexValue) current.getElement();
      current = currentVertexValue.getCameFrom();
    }
    finalPath.add(start);
    return finalPath;
  }

  private HashMap<String, Object> find(Vertice start, Vertice goal) {
    Vector<Vertice> openList = new Vector<Vertice>() {
      {
        add(start);
      }
    };
    VertexValue startVertexValue = (VertexValue) start.getElement();
    startVertexValue.setGScore(0);
    startVertexValue.setHScore(goal);
    while (!openList.isEmpty()) {
      Vertice current = getLowestCost(openList);
      VertexValue currentVertexValue = (VertexValue) current.getElement();
      if (current == goal) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("fScore", currentVertexValue.getFScore());
        result.put("finalPath", reconstructPath(start, current));
        return result;
      }
      openList.remove(current);
      Iterator<Aresta> edges = this.graph.arestasIncidentes(current);
      while (edges.hasNext()) {
        Aresta edge = edges.next();
        Vertice neighborVertex = this.graph.oposto(current, edge);
        VertexValue neighborVertexValue = (VertexValue) neighborVertex.getElement();
        double newGScore = currentVertexValue.getGScore() + calculateWeight(current, neighborVertex);
        if (newGScore < neighborVertexValue.getGScore()) {
          neighborVertexValue.setCameFrom(current);
          neighborVertexValue.setHScore(goal);
          neighborVertexValue.setGScore(newGScore);
          if (openList.contains(neighborVertex) == false) {
            openList.add(neighborVertex);
          }
        }
      }
    }

    return null;
  }

  private void print(Vector<Vector<Vertice>> list, Vector<Vertice> finalPath, Vector<Vertice> starts,
      Vector<Vertice> goals) {
    for (Vector<Vertice> line : list) {
      for (Vertice column : line) {
        if (goals.contains(column)) {
          System.out.print("*");
        } else if (starts.contains(column)) {
          System.out.print("+");
        } else if (finalPath.contains(column)) {
          System.out.print("-");
        } else if (column == null) {
          System.out.print("/");
        } else {
          System.out.print("&");
        }
      }
      System.out.println();
    }
  }
}
