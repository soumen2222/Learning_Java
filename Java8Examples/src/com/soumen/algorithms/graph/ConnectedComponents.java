package com.soumen.algorithms.graph;

import java.util.ArrayList;
import java.util.List;

public class ConnectedComponents {

    private List<Integer> connectedList = new ArrayList<>();
    private Graph g ;
    ConnectedComponents(Graph g){
        for (int i = 0; i < g.getEdges(); i++) {
            connectedList.add(-1);
        }
    }

    public void dfsConnected(int node,int count){
        if(connectedList.get(node)!=-1) return;
        connectedList.set(node,count);
        List<Integer> neighbours = g.getAdjList().get(node);
        for(Integer next: neighbours)
            dfsConnected(next,count);
    }

    public void connected(){
        int count =0;
        for (int i = 0; i < g.getEdges(); i++){
            dfsConnected(i,count++);
        }
    }

    public static void main(String[] args)
    {
        // Creating a graph with 5 vertices
        int V = 5;
        Graph g = new Graph(V);
        ConnectedComponents c = new ConnectedComponents(g);
        // Adding edges one by one
        g.addEdge( 0, 1);
        g.addEdge(0, 4);
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
        g.printGraph();
        c.connected();
    }
}
