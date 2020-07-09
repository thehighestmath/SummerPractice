package ru.etu.practice;

import java.util.*;

public class Graph {
//    private static final int SIZE = 26;

    private List<Edge> inputEdges;
    private List<Node> inputVertices;
    public boolean isModified = false;

//    public

    public List<Edge> getInputEdges() {
        return inputEdges;
    }

    public List<Node> getInputVertices() {
        return inputVertices;
    }

    public Graph() {
        this.inputEdges = new LinkedList<>();
        this.inputVertices = new LinkedList<>();
    }

//    public void initGraph(List<Edge> inputEdges, List<Node> inputVertices) {
//        this.inputEdges = new LinkedList<>(inputEdges);
//        this.inputVertices = new LinkedList<>(inputVertices);
//    }

    public void addEdge(Node from, Node to, int distance) {
        Edge newEdge = new Edge(from, to, distance);
        for (Edge edge : inputEdges)
            if (edge.equalsWay(newEdge))
                return;
        inputEdges.add(newEdge);
        isModified = true;
    }

    public void addVertex(Node node) {
        if (!inputVertices.contains(node))
            inputVertices.add(node);
        isModified = true;
    }

    public void addVertex() {
        int len = inputVertices.size();
        inputVertices.add(new Node((char)('a' + len)));
    }

    public void clear() {
        inputVertices.clear();
        inputEdges.clear();
        isModified = false;
    }
}
