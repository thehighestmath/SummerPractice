package ru.etu.practice;

import java.util.*;

public class Graph {
    private static final int SIZE = 26;

    private List<Edge> inputEdges;
    private Set<Node> inputVertices;

    public List<Edge> getInputEdges() {
        return inputEdges;
    }

    public Set<Node> getInputVertices() {
        return inputVertices;
    }

    public Graph() {

    }

    public void initGraph(List<Edge> inputEdges, List<Node> inputVertices) {
        this.inputEdges = new LinkedList<>(inputEdges);
        this.inputVertices = new HashSet<>(inputVertices);
    }
}
