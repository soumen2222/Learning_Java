package com.soumen.algorithms.graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private List<List<Integer>> adjList = new ArrayList<>();
    private Integer edges;
    Graph(int edges){
        for (int i = 0; i < edges; i++) {
            adjList.add(new ArrayList<Integer>());
        }
        this.edges =edges;
    }

    public void printGraph()
    {
        for (int i = 0; i < adjList.size(); i++) {
            System.out.println("\nAdjacency list of vertex" + i);
            System.out.print("head");
            for (int j = 0; j < adjList.get(i).size(); j++) {
                System.out.print(" -> "+adjList.get(i).get(j));
            }
            System.out.println();
        }
    }
    public void addEdge(int u, int v)
    {
        adjList.get(u).add(v);
        adjList.get(v).add(u);
    }

    public List<List<Integer>> getAdjList() {
        return adjList;
    }

    public Integer getEdges() {
        return edges;
    }

    public static void main(String[] args)
    {
        // Creating a graph with 5 vertices
        int V = 5;
        Graph g = new Graph(V);
        // Adding edges one by one
        g.addEdge( 0, 1);
        g.addEdge(0, 4);
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
        g.printGraph();
    }
}
