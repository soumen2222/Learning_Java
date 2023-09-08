package com.soumen.algorithms.graph;

import java.util.*;

public class BFS {

    private Graph g ;
    public BFS(Graph g){
        this.g = g;
    }
    public List<Integer> bfs(int startNode){
        List<Boolean> visited = new ArrayList<>();
        List<Integer> prev = new ArrayList<>();
        for (int i = 0; i < g.getEdges(); i++) {
            visited.add(Boolean.FALSE);
            prev.add(null);
        }
        visited.set(startNode,Boolean.TRUE);
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startNode);

        while(!queue.isEmpty()){
            Integer processingNode = queue.poll();
           // System.out.print(processingNode+" ");
            for (Integer n : g.getAdjList().get(processingNode)) {
                if(!visited.get(n).equals(Boolean.TRUE)) {
                    visited.set(n, Boolean.TRUE);
                    queue.add(n);
                    prev.set(n,processingNode);
                }
            }
        }
        return prev;
    }

    public void bfsShortestPath(int startNode, int endNode){
        List<Boolean> visited = new ArrayList<>();
        for (int i = 0; i < g.getEdges(); i++) {
            visited.add(Boolean.FALSE);
        }
        List<Integer> prev = bfs(startNode);
        List<Integer> path = reConstructPath(startNode,endNode,prev);
        path.forEach(System.out::println);
    }

    private List<Integer> reConstructPath(int startNode, int endNode, List<Integer> prev) {
        List<Integer> path = new ArrayList<>();
       for(Integer at = endNode; at!=null ; at = prev.get(at)){
           path.add(at);
       }
       Collections.reverse(path);
       if(path.get(0).equals(startNode)){
           return path;
       }else{
           return new ArrayList<>();
       }
    }


    public static void main(String[] args)
    {
       Graph g = new Graph(13);
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        g.addEdge(2, 5);
        g.addEdge(2, 6);
        g.addEdge(5, 9);
        g.addEdge(5, 10);
        g.addEdge(4, 7);
        g.addEdge(4, 8);
        g.addEdge(7, 11);
        g.addEdge(7, 12);

        BFS dfsTest = new BFS(g);
        System.out.println("Following is Breadth First Traversal "+
                "(starting from vertex 1)");
        dfsTest.bfsShortestPath(5,12);
    }


}
