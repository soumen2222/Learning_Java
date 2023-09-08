package com.soumen.algorithms.graph;

import java.util.ArrayList;
import java.util.List;

public class DFS {
    private List<Boolean> visited = new ArrayList<>();
    private Graph g ;
    public DFS(Graph g){
        this.g = g;
        for (int i = 0; i < g.getEdges(); i++) {
            visited.add(Boolean.FALSE);
        }
    }
    public void dfs(int node){
        if(visited.get(node).equals(Boolean.TRUE)) return;
        visited.set(node,Boolean.TRUE);
        System.out.print("Visiting Node->:" + node);
        List<Integer> neighbours = g.getAdjList().get(node);
        for(Integer next: neighbours)
            dfs(next);
    }

    public static void main(String[] args)
    {
        // Creating a graph with 5 vertices
        int V = 5;
        Graph g = new Graph(V);
        DFS dfsTest = new DFS(g);
        // Adding edges one by one
        g.addEdge( 0, 1);
        g.addEdge(0, 4);
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
        g.printGraph();
        dfsTest.dfs(0);
    }
}
